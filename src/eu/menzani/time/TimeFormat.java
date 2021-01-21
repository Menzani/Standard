package eu.menzani.time;

import java.text.DecimalFormat;

public class TimeFormat {
    private static final DecimalFormat doubleFormat = new DecimalFormat("#.##");

    public static String formatComputerTime(double nanoseconds) {
        if (nanoseconds < 2_000D) {
            return doubleFormat.format(nanoseconds) + "ns";
        }
        if (nanoseconds < 2_000_000D) {
            return doubleFormat.format(nanoseconds / 1_000D) + "us";
        }
        return doubleFormat.format(nanoseconds / 1_000_000D) + "ms";
    }

    public static String formatComputerTime(long nanoseconds) {
        if (nanoseconds < 2_000L) {
            return nanoseconds + "ns";
        }
        if (nanoseconds < 2_000_000L) {
            return Math.round(nanoseconds / 1_000D) + "us";
        }
        return Math.round(nanoseconds / 1_000_000D) + "ms";
    }

    public static String formatThroughput(double nanoseconds) {
        if (nanoseconds < 1_000D) {
            return doubleFormat.format(1_000D / nanoseconds) + "M msg/sec";
        }
        if (nanoseconds < 1_000_000D) {
            return doubleFormat.format(1_000_000D / nanoseconds) + "K msg/sec";
        }
        return doubleFormat.format(1_000_000_000D / nanoseconds) + " msg/sec";
    }
}
