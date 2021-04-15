package eu.menzani.benchmark;

public class Profiler {
    private final Result result;
    private final double divideBy;

    private long start;

    public Profiler(Object object, int divideBy) {
        this(object, divideBy, ResultFormat.TIME);
    }

    public Profiler(Object object, int divideBy, ResultFormat resultFormat) {
        this(object.getClass(), divideBy, resultFormat);
    }

    public Profiler(Class<?> clazz, int divideBy) {
        this(clazz, divideBy, ResultFormat.TIME);
    }

    public Profiler(Class<?> clazz, int divideBy, ResultFormat resultFormat) {
        this(clazz.getSimpleName(), divideBy, resultFormat);
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
