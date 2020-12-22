/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.menzani.benchmark;

import eu.menzani.lang.Check;
import eu.menzani.lang.Lang;
import eu.menzani.lang.UncaughtException;
import eu.menzani.system.SystemProperty;
import eu.menzani.system.ThreadManipulation;
import eu.menzani.system.ThreadManipulationSpread;
import eu.menzani.system.Threads;

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

    public void runBenchmark(BenchmarkListener listener) {
        Class<?> clazz = getClass();
        List<String> command = List.of(
                getJavaRuntime().toString(),
                "-Xms8g",
                "-Xmx8g",
                "-XX:+UseLargePages",
                "-XX:+AlwaysPreTouch",
                "-XX:+UnlockExperimentalVMOptions",
                "-XX:+UseEpsilonGC",
                "-XX:-RestrictContended",
                "-XX:-UseBiasedLocking",
                "--add-opens",
                "java.base/jdk.internal.misc=" + Lang.EU_MENZANI_MODULE.getName(),
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

    protected void runBenchmark() {
        Runtime runtime = Runtime.getRuntime();
        int numIterations = getNumIterations();
        int concurrency = getConcurrency();
        Check.notLesser(concurrency, 1);
        long warmupMemoryCap = runtime.totalMemory() / 2L;

        long startMemory = runtime.freeMemory();
        test(numIterations);
        long endMemory = runtime.freeMemory();
        long testMemoryCap = (startMemory - endMemory) * 3L;

        if (concurrency == 1) {
            getThreadManipulation().applyToCurrentThread();

            long start = System.nanoTime();
            do {
                test(numIterations);
            } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > warmupMemoryCap);
            synchronized (this) {
                results.clear();
            }
            do {
                test(numIterations);
            } while (System.nanoTime() - start < 6L * 1_000_000_000L && runtime.freeMemory() > testMemoryCap);
        } else {
            Thread[] threads = new Thread[concurrency];
            ThreadManipulationSpread threadManipulation = getConcurrentThreadManipulation();
            for (int i = 0; i < concurrency; i++) {
                threads[i] = new BenchmarkThread(threadManipulation, this, warmupMemoryCap);
            }
            for (Thread thread : threads) {
                thread.start();
            }
            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                throw new AssertionError();
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
            try {
                for (Thread thread : threads) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                throw new AssertionError();
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
