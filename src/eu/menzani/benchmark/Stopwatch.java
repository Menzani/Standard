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
        System.out.println(prefix + TimeFormat.formatComputerTime(end - start));
    }
}
