package eu.menzani.misc;

import eu.menzani.lang.Ensure;

public class Application {
    private static volatile String name;

    public static void setName(String name) {
        Application.name = name;
    }

    public static String getName() {
        String name = Application.name;
        Ensure.notNull(name);
        return name;
    }
}
