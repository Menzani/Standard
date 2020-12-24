package eu.menzani.misc;

public class Strings {
    public static String firstLetterToUppercase(String string) {
        if (string.isEmpty()) {
            return string;
        }
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
}
