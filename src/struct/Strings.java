package eu.menzani.struct;

public class Strings {
    public static final String LN = System.lineSeparator();

    public static String firstLetterToUppercase(String string) {
        if (string.isEmpty()) {
            return string;
        }
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public static boolean isInteger(String string) {
        return Patterns.ONLY_DIGITS.matcher(string).matches();
    }
}
