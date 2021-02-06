package eu.menzani.struct;

import eu.menzani.lang.Ensure;

public class AppInfo {
    // Cannot be opaque because we need to perform the least amount of initialization
    // since setName() might need to be called before adding actions to the GlobalExceptionHandler
    private static volatile String name;

    public static void setName(String name) {
        AppInfo.name = name;
    }

    public static String getName() {
        String name = AppInfo.name;
        Ensure.notNull(name);
        return name;
    }
}
