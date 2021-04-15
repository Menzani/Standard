package eu.menzani.benchmark;

import eu.menzani.collection.FixedList;
import eu.menzani.concurrent.ThreadGroup;
import eu.menzani.io.JavaProcessLauncher;
import eu.menzani.lang.Check;
import eu.menzani.lang.Classes;
import eu.menzani.object.ObjectFactory;
import eu.menzani.struct.JVMOption;
import eu.menzani.struct.MemorySize;
import eu.menzani.system.*;

public abstract class Benchmark {
    static Benchmark current;

    private final FixedList<Result> results = new FixedList<>(10);

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

    protected ResultFormat getAutoProfileResultFormat() {
        return ResultFormat.TIME;
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

    protected MemorySize getHeapSize() {
        return MemorySize.ofGigabytes(8);
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
            launcher.setFixedHeap(getHeapSize());
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
                        Classes.getFullName(clazz)
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

            run(numIterations, runtime, warmupMemoryCap);
            synchronized (this) {
                results.clear();
            }
            run(numIterations, runtime, testMemoryCap);
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

    private void run(int numIterations, Runtime runtime, long memoryCap) {
        if (shouldAutoProfile()) {
            Profiler profiler = new Profiler(this, getAutoProfileDivideBy(), getAutoProfileResultFormat());
            long start = System.nanoTime();
            do {
                profiler.start();
                test(numIterations);
                profiler.stop();
                onTestEnd();
            } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > memoryCap);
        } else {
            long start = System.nanoTime();
            do {
                test(numIterations);
            } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > memoryCap);
        }
    }

    protected abstract void test(int i);

    protected void onTestEnd() {
    }

    private class BenchmarkThreadFactory implements ObjectFactory<Thread> {
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
        public Thread newInstance() {
            return new BenchmarkThread(threadManipulation, Benchmark.this, memoryCap);
        }
    }
}
