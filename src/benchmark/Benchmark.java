package eu.menzani.benchmark;

import eu.menzani.concurrent.ThreadGroup;
import eu.menzani.io.JavaProcessLauncher;
import eu.menzani.lang.Check;
import eu.menzani.lang.Module;
import eu.menzani.struct.JVMOption;
import eu.menzani.struct.MemorySize;
import eu.menzani.system.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    protected int getAutoProfileDivideBy() {
        return getNumIterations();
    }

    protected boolean shouldAutoProfile() {
        return true;
    }

    protected boolean shouldPrintCompilation() {
        return false;
    }

    protected boolean shouldPrintInlining() {
        return false;
    }

    protected boolean shouldPrintAssembly() {
        return false;
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
                    JVMOption.ENABLE_CONTENDED_ANNOTATION,
                    JVMOption.DISABLE_BIASED_LOCKING,
                    JVMOption.DISABLE_PERF_DATA
            );
            if (shouldPrintCompilation()) {
                launcher.addJVMOption(JVMOption.PRINT_COMPILATION);
            }
            boolean shouldPrintInlining = shouldPrintInlining();
            boolean shouldPrintAssembly = shouldPrintAssembly();
            if (shouldPrintInlining || shouldPrintAssembly) {
                launcher.addJVMOption(JVMOption.UNLOCK_DIAGNOSTIC_VM_OPTIONS);
                if (shouldPrintInlining) {
                    launcher.addJVMOption(JVMOption.PRINT_INLINING);
                }
                if (shouldPrintAssembly) {
                    launcher.addJVMOptions(
                            JVMOption.PRINT_ASSEMBLY,
                            JVMOption.DISABLE_TIERED_COMPILATION,
                            JVMOption.BATCH
                    );
                }
            }

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

        int numIterations = getNumIterations();
        Runtime runtime = Runtime.getRuntime();
        long warmupMemoryCap = runtime.totalMemory() / 2L;

        long startMemory = runtime.freeMemory();
        test(numIterations);
        long endMemory = runtime.freeMemory();
        long testMemoryCap = (startMemory - endMemory) * 2L;

        if (concurrency == 1) {
            getThreadManipulation().applyToCurrentThread();

            if (shouldAutoProfile()) {
                int autoProfileDivideBy = getAutoProfileDivideBy();
                Profiler profiler = new Profiler(this, autoProfileDivideBy);
                long start = System.nanoTime();
                do {
                    profiler.start();
                    test(numIterations);
                    profiler.stop();
                } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > warmupMemoryCap);
                synchronized (this) {
                    results.clear();
                }
                profiler = new Profiler(this, autoProfileDivideBy);
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
            ThreadGroup threads = new ThreadGroup(concurrency);
            BenchmarkThreadFactory factory = new BenchmarkThreadFactory(getConcurrentThreadManipulation());
            factory.setMemoryCap(warmupMemoryCap);
            threads.run(factory);
            synchronized (this) {
                results.clear();
            }
            factory.setMemoryCap(testMemoryCap);
            threads.run(factory);
        }

        synchronized (this) {
            for (Result result : results) {
                result.report();
            }
        }
    }

    protected abstract void test(int i);

    private class BenchmarkThreadFactory implements Supplier<Thread> {
        private final ThreadManipulationSpread threadManipulation;
        private long memoryCap;

        BenchmarkThreadFactory(ThreadManipulationSpread threadManipulation) {
            this.threadManipulation = threadManipulation;
        }

        void setMemoryCap(long memoryCap) {
            this.memoryCap = memoryCap;
            threadManipulation.reset();
        }

        @Override
        public Thread get() {
            return new BenchmarkThread(threadManipulation, Benchmark.this, memoryCap);
        }
    }
}
