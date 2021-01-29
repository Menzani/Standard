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

    public static String formatThroughput(double nanoseconds) {
        if (nanoseconds < 1_000D) {
            return doubleFormat.format(1_000D / nanoseconds) + "M msg/sec";
        }
        if (nanoseconds < 1_000_000D) {
            return doubleFormat.format(1_000_000D / nanoseconds) + "K msg/sec";
        }
        return doubleFormat.format(1_000_000_000D / nanoseconds) + " msg/sec";
    }

    public static String formatExecutionTime(long nanoseconds) {
        if (nanoseconds < 2_000L) {
            return nanoseconds + "ns";
        }
        if (nanoseconds < 2_000_000L) {
            return Math.round(nanoseconds / 1_000D) + "us";
        }
        if (nanoseconds < 2_000_000_000L) {
            return Math.round(nanoseconds / 1_000_000D) + "ms";
        }
        long secondsPart = (nanoseconds / 1_000_000_000L) % 60L;
        long minutesPart = (nanoseconds / (1_000_000_000L * 60L)) % 60L;
        long hoursPart = (nanoseconds / (1_000_000_000L * 60L * 60L)) % 24L;
        long daysPart = nanoseconds / (1_000_000_000L * 60L * 60L * 24L);
        String text = "";
        if (daysPart != 0L) text += daysPart + "d ";
        if (hoursPart != 0L) text += hoursPart + "h ";
        if (minutesPart != 0L) text += minutesPart + "m ";
        if (secondsPart != 0L) text += secondsPart + 's';
        return text;
    }
}
