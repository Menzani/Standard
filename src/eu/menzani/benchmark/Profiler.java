package eu.menzani.benchmark;

public class Profiler {
    private final Result result;
    private final double divideBy;

    private long start;

    public Profiler(Object object, int divideBy) {
        this(object.getClass(), divideBy);
    }

    public Profiler(Class<?> clazz, int divideBy) {
        this(clazz.getSimpleName(), divideBy);
    }

    public Profiler(String name, int divideBy) {
        this(name, divideBy, ResultFormat.TIME);
    }

    public Profiler(String name, int divideBy, ResultFormat resultFormat) {
        this.divideBy = divideBy;
        result = Benchmark.current.getResult(name, resultFormat);
    }

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        final long end = System.nanoTime();
        result.update((end - start) / divideBy);
    }
}
