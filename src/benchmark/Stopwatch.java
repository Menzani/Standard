package eu.menzani.benchmark;

import eu.menzani.atomic.AtomicLong;
import eu.menzani.io.PrintToConsoleException;
import eu.menzani.lang.Lang;
import eu.menzani.lang.Optional;
import eu.menzani.time.TimeFormat;

public class Stopwatch {
    private String prefix;
    private long minimumToReport;

    private long start;

    public Stopwatch() {
        this(null);
    }

    public Stopwatch(String prefix) {
        setPrefix(prefix);
        start = System.nanoTime();
    }

    public void setPrefix(@Optional String prefix) {
        if (prefix == null) {
            this.prefix = "";
        } else {
            this.prefix = prefix + " in ";
        }
    }

    public void setMinimumToReport(long minimumToReport) {
        this.minimumToReport = minimumToReport;
    }

    public void disableReport() {
        minimumToReport = Long.MAX_VALUE;
    }

    public void enableReport() {
        minimumToReport = 0L;
    }

    public void stop() {
        final long end = System.nanoTime();
        long elapsed = calculateElapsed(end);
        if (elapsed >= minimumToReport) {
            System.out.println(prefix + TimeFormat.formatExecutionTime(elapsed));
        }
    }

    public void sum() {
        final long end = System.nanoTime();
        Sum.INSTANCE.add(calculateElapsed(end));
    }

    private long calculateElapsed(long end) {
        long elapsed = end - start;
        if (elapsed < 300L) {
            throw new PrintToConsoleException("Too little granularity.");
        }
        return elapsed;
    }

    private static class Sum extends Thread {
        static final Sum INSTANCE = new Sum();

        static {
            Runtime.getRuntime().addShutdownHook(INSTANCE);
        }

        private static final long SUM = Lang.objectFieldOffset(Sum.class, "sum");

        private long sum;

        private Sum() {
        }

        void add(long value) {
            AtomicLong.addVolatile(this, SUM, value);
        }

        @Override
        public void run() {
            System.out.println(TimeFormat.formatComputerTime(sum));
        }
    }
}
