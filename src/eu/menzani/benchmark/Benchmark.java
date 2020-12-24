package eu.menzani.benchmark;

import eu.menzani.lang.Check;
import eu.menzani.lang.Nonblocking;
import eu.menzani.lang.UncaughtException;
import eu.menzani.system.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class Benchmark {
    static Benchmark current;

    private final List<Result> results = new ArrayList<>();

    protected Benchmark() {
        current = this;
    }

    synchronized Result getResult(String profilerName, ResultFormat format) {
        for (Result result : results) {
            if (result.profilerName.equals(profilerName)) {
                return result;
            }
        }
        Result result = new Result(profilerName, format);
        results.add(result);
        return result;
    }

    protected int getNumIterations() {
        return 1_000_000;
    }

    protected int getConcurrency() {
        return 1;
    }

    protected ThreadManipulation getThreadManipulation() {
        return ThreadManipulation.setPriorityToRealtimeAndBindToCPU(2);
    }

    protected ThreadManipulationSpread getConcurrentThreadManipulation() {
        return ThreadManipulationSpread.setPriorityToRealtimeAndBind(
                Threads.spreadOverCPUs()
                        .fromFirstCPU()
                        .toLastCPU()
                        .skipHyperthreads()
                        .build());
    }

    protected Path getJavaRuntime() {
        return Path.of("java");
    }

    protected boolean shouldAutoProfile() {
        return true;
    }

    public void launchBenchmark() {
        launchBenchmark(ConsoleBenchmarkListener.INSTANCE);
    }

    public void launchBenchmark(BenchmarkListener listener) {
        ApplicationProperty launched = new ApplicationProperty(Benchmark.class, "launched");
        if (launched.getAsBoolean()) {
            runBenchmark();
        } else {
            Class<?> clazz = getClass();
            List<String> command = List.of(
                    getJavaRuntime().toString(),
                    launched.toString(),
                    "-Xms8g",
                    "-Xmx8g",
                    "-XX:+UseLargePages",
                    "-XX:+AlwaysPreTouch",
                    "-XX:+UnlockExperimentalVMOptions",
                    "-XX:+UseEpsilonGC",
                    "-XX:-RestrictContended",
                    "-XX:-UseBiasedLocking",
                    "-p",
                    new SystemProperty("jdk.module.path").get(),
                    "-m",
                    clazz.getModule().getName() + '/' + clazz.getName()
            );

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            try {
                listener.beginProcessCreate();
                Process process = builder.start();
                listener.onProcessCreated(process);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.ISO_8859_1))) {
                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line);
                        output.append('\n');

                        listener.onOutputLineAdded(line);
                        listener.updateOutput(output.toString());
                    }
                    listener.onEnd();
                }
            } catch (IOException e) {
                throw new UncaughtException(e);
            }
        }
    }

    protected void runBenchmark() {
        int concurrency = getConcurrency();
        Check.notLesser(concurrency, 1);
        Runtime runtime = Runtime.getRuntime();
        long warmupMemoryCap = runtime.totalMemory() / 2L;
        int numIterations = getNumIterations();

        long startMemory = runtime.freeMemory();
        test(numIterations);
        long endMemory = runtime.freeMemory();
        long testMemoryCap = (startMemory - endMemory) * 3L;

        if (concurrency == 1) {
            getThreadManipulation().applyToCurrentThread();

            if (shouldAutoProfile()) {
                Profiler profiler = new Profiler(this, numIterations);
                long start = System.nanoTime();
                do {
                    profiler.start();
                    test(numIterations);
                    profiler.stop();
                } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > warmupMemoryCap);
                synchronized (this) {
                    results.clear();
                }
                profiler = new Profiler(this, numIterations);
                start = System.nanoTime();
                do {
                    profiler.start();
                    test(numIterations);
                    profiler.stop();
                } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > testMemoryCap);
            } else {
                long start = System.nanoTime();
                do {
                    test(numIterations);
                } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > warmupMemoryCap);
                synchronized (this) {
                    results.clear();
                }
                start = System.nanoTime();
                do {
                    test(numIterations);
                } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > testMemoryCap);
            }
        } else {
            Thread[] threads = new Thread[concurrency];
            ThreadManipulationSpread threadManipulation = getConcurrentThreadManipulation();
            for (int i = 0; i < concurrency; i++) {
                threads[i] = new BenchmarkThread(threadManipulation, this, warmupMemoryCap);
            }
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                Nonblocking.join(thread);
            }
            synchronized (this) {
                results.clear();
            }
            threadManipulation.reset();
            for (int i = 0; i < concurrency; i++) {
                threads[i] = new BenchmarkThread(threadManipulation, this, testMemoryCap);
            }
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                Nonblocking.join(thread);
            }
        }

        synchronized (this) {
            for (Result result : results) {
                result.report();
            }
        }
    }

    protected abstract void test(int i);
}
