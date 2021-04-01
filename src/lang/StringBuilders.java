package eu.menzani.lang;

public class StringBuilders {
    public static boolean equals(StringBuilder left, StringBuilder right) {
        int length = left.length();
        if (length != right.length()) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (left.charAt(i) != right.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(String string, StringBuilder builder, int builderFrom, int builderTo) {
        if (string.length() != builderTo - builderFrom) {
            return false;
        }
        for (int i = 0; builderFrom < builderTo; builderFrom++) {
            if (string.charAt(i++) != builder.charAt(builderFrom)) {
                return false;
            }
        }
        return true;
    }

    // Developed from java.lang.StringUTF16.hashCode()
    public static int hashCode(StringBuilder builder) {
        int result = 0;
        for (int i = 0, length = builder.length() >> 1; i < length; i++) {
            result = 31 * result + builder.charAt(i);
        }
        return result;
    }

    public static void replace(StringBuilder builder, TargetReplacement... targetReplacements) {
        replace(builder, 0, builder.length(), targetReplacements);
    }

    public static void replace(StringBuilder builder, int from, int to, TargetReplacement... targetReplacements) {
        while (from < to) {
            char character = builder.charAt(from);
            boolean noneMatched = true;
            for (TargetReplacement targetReplacement : targetReplacements) {
                if (character == targetReplacement.getTarget()) {
                    CharSequence replacement = targetReplacement.getReplacement();
                    builder.setCharAt(from, replacement.charAt(0));
                    int length = replacement.length();
                    builder.insert(from + 1, replacement, 1, length);
                    from += length;
                    to += length - 1;
                    noneMatched = false;
                    break;
                }
            }
            if (noneMatched) {
                from++;
            }
        }
    }
}