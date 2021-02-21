package eu.menzani.benchmark;

import eu.menzani.io.JavaProcessLauncher;
import eu.menzani.lang.Check;
import eu.menzani.lang.Module;
import eu.menzani.lang.Nonblocking;
import eu.menzani.struct.JVMOption;
import eu.menzani.struct.MemorySize;
import eu.menzani.system.*;

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
            JavaProcessLauncher launcher = new JavaProcessLauncher();
            launcher.addProperty(launched.setAsBoolean(true));
            launcher.setFixedHeap(MemorySize.ofGigabytes(8));
            launcher.addJVMOptions(
                    JVMOption.USE_LARGE_PAGES,
                    JVMOption.ALWAYS_PRE_TOUCH,
                    JVMOption.UNLOCK_EXPERIMENTAL_VM_OPTIONS,
                    JVMOption.USE_EPSILON_GC,
                    JVMOption.DO_NOT_RESTRICT_CONTENDED,
                    JVMOption.DO_NOT_USE_BIASED_LOCKING
            );

            Class<?> clazz = getClass();
            if (SystemProperty.MODULE_PATH_STRING == null) {
                launcher.addArguments(
                        "-cp",
                        SystemProperty.CLASS_PATH_STRING,
                        clazz.getName()
                );
            } else {
                launcher.addArguments(
                        "-p",
                        SystemProperty.MODULE_PATH_STRING,
                        "-m",
                        Module.of(clazz).getNameEmptyOrWithSlash() + clazz.getName()
                );
            }

            listener.beginProcessCreate();
            launcher.start();
            listener.onProcessCreated(launcher.getProcess());

            new OutputOrErrorIteratorThread(launcher, listener, true).start();
            new OutputOrErrorIteratorThread(launcher, listener, false).doRun();
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
