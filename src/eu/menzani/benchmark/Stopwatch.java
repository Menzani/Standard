package eu.menzani.benchmark;

import eu.menzani.time.TimeFormat;

public class Stopwatch {
    private String prefix;
    private long start;

    public Stopwatch() {
        this("");
    }

    public Stopwatch(String prefix) {
        this.prefix = prefix;
        start = System.nanoTime();
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void stop() {
        final long end = System.nanoTime();
        System.out.println(prefix + TimeFormat.formatExecutionTime(end - start));
    }

    public void sum() {
        final long end = System.nanoTime();
        Sum.INSTANCE.sum += end - start;
    }

    private static class Sum extends Thread {
        static final Sum INSTANCE = new Sum();

        static {
            Runtime.getRuntime().addShutdownHook(INSTANCE);
        }

        private Sum() {
        }

        long sum;

        @Override
        public void run() {
            System.out.println(TimeFormat.formatComputerTime(sum));
        }
    }
}
