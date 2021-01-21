package eu.menzani.benchmark;

import eu.menzani.time.TimeFormat;

class Result {
    final String profilerName;
    private final ResultFormat format;

    private double sum;
    private double count;
    private double minimum = Double.MAX_VALUE;
    private double maximum;

    Result(String profilerName, ResultFormat format) {
        this.profilerName = profilerName;
        this.format = format;
    }

    synchronized void update(double value) {
        sum += value;
        count++;
        if (value < minimum) {
            minimum = value;
        }
        if (value > maximum) {
            maximum = value;
        }
    }

    void report() {
        double sum;
        double count;
        double minimum;
        double maximum;
        synchronized (this) {
            sum = this.sum;
            count = this.count;
            minimum = this.minimum;
            maximum = this.maximum;
        }
        double average = sum / count;
        String report = profilerName + ": " + formatAverage(average);
        if (count > 1D && maximum != 0D) {
            double absoluteVariance = Math.max(maximum - average, average - minimum);
            long relativeVariance = Math.round(absoluteVariance / average * 100D);
            report += " ± " + relativeVariance + '%';
        }
        System.out.println(report);
    }

    private String formatAverage(double average) {
        switch (format) {
            case TIME:
                return TimeFormat.formatComputerTime(average);
            case THROUGHPUT:
                return TimeFormat.formatThroughput(average);
            default:
                throw new AssertionError();
        }
    }
}
