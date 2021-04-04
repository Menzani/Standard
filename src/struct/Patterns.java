package eu.menzani.struct;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern SPLIT_BY_NEWLINE = Pattern.compile("[\r\n]+");
    public static final Pattern ONLY_DIGITS = Pattern.compile("^[0-9]+$");

    private static final Pattern replaceLineSeparators = Pattern.compile("\r\n?");

    public static String normalizeLineSeparators(String string) {
        return replaceLineSeparators.matcher(string).replaceAll(Strings.LN);
    }
}
