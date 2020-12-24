package eu.menzani.misc;

public class Debug {
    public static void log(String message, Object... values) {
        message = Strings.firstLetterToUppercase(message);
        if (values.length == 1) {
            System.out.println(message + ": " + values[0]);
        } else if (values.length != 0) {
            StringBuilder output = new StringBuilder(message);
            output.append(':');
            String lineSeparator = System.lineSeparator();
            output.append(lineSeparator);
            for (Object value : values) {
                output.append("    ");
                output.append(value);
                output.append(lineSeparator);
            }
            System.out.print(output);
        }
    }
}
