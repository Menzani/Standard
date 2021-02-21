package eu.menzani.struct;

import eu.menzani.lang.Ensure;
import eu.menzani.lang.Optional;

public class AppInfo {
    // Cannot be opaque because we need to perform the least amount of initialization
    // since setName() might need to be called before adding actions to the GlobalExceptionHandler
    private static volatile String name;
    private static volatile String projectName;

    public static void setName(String name) {
        AppInfo.name = name;
    }

    public static String getName() {
        String name = AppInfo.name;
        Ensure.notNull(name);
        return name;
    }

    public static void setProjectName(String projectName) {
        AppInfo.projectName = projectName;
    }

    public static @Optional String getProjectName() {
        return AppInfo.projectName;
    }
}
