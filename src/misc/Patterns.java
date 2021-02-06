package eu.menzani.misc;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern SPLIT_BY_NEWLINE = Pattern.compile("[\r\n]+");
    public static final Pattern SPLIT_BY_SPACE = Pattern.compile(" ");

    private static final Pattern replaceLineSeparators = Pattern.compile("\r\n?");

    public static String normalizeLineSeparators(String string) {
        return replaceLineSeparators.matcher(string).replaceAll(System.lineSeparator());
    }
}
