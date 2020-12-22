package eu.menzani.benchmark;

import java.text.DecimalFormat;

class Result {
    private static final DecimalFormat doubleFormat = new DecimalFormat("#.##");

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
        String report = profilerName + ": " + formatDouble(average);
        if (count > 1D && maximum != 0D) {
            double absoluteVariance = Math.max(maximum - average, average - minimum);
            long relativeVariance = Math.round(absoluteVariance / average * 100D);
            report += " ± " + relativeVariance + '%';
        }
        System.out.println(report);
    }

    private String formatDouble(double value) {
        switch (format) {
            case TIME:
                if (value < 2_000D) {
                    return doubleFormat.format(value) + "ns";
                }
                if (value < 2_000_000D) {
                    return doubleFormat.format(value / 1_000D) + "us";
                }
                return doubleFormat.format(value / 1_000_000D) + "ms";
            case THROUGHPUT:
                if (value < 1_000D) {
                    return doubleFormat.format(1_000D / value) + "M msg/sec";
                }
                if (value < 1_000_000D) {
                    return doubleFormat.format(1_000_000D / value) + "K msg/sec";
                }
                return doubleFormat.format(1_000_000_000D / value) + " msg/sec";
            default:
                throw new AssertionError();
        }
    }
}
