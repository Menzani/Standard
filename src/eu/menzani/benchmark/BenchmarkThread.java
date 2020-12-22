package eu.menzani.benchmark;

import eu.menzani.system.ThreadManipulationSpread;

class BenchmarkThread extends Thread {
    private final ThreadManipulationSpread threadManipulation;
    private final Benchmark benchmark;
    private final long memoryCap;

    BenchmarkThread(ThreadManipulationSpread threadManipulation, Benchmark benchmark, long memoryCap) {
        this.threadManipulation = threadManipulation;
        this.benchmark = benchmark;
        this.memoryCap = memoryCap;
    }

    @Override
    public void run() {
        threadManipulation.applyToNextThread();

        Benchmark benchmark = this.benchmark;
        int numIterations = benchmark.getNumIterations();
        Runtime runtime = Runtime.getRuntime();
        long memoryCap = this.memoryCap;

        long start = System.nanoTime();
        do {
            benchmark.test(numIterations);
        } while (System.nanoTime() - start < 3L * 1_000_000_000L && runtime.freeMemory() > memoryCap);
    }
}
