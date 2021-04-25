package eu.menzani.struct;

import eu.menzani.lang.Ensure;

import java.util.HashMap;
import java.util.Map;

// We need to perform the least amount of initialization since setName() might need to be called
// before adding actions to the GlobalExceptionHandler
public class AppInfo {
    private static volatile String name;
    private static Map<Module, String> moduleDisplayNames;

    public static void setName(String name) {
        AppInfo.name = name;
    }

    public static String getName() {
        String name = AppInfo.name;
        Ensure.notNull(name);
        return name;
    }

    public static synchronized void setModuleDisplayName(Module module, String displayName) {
        if (moduleDisplayNames == null) {
            moduleDisplayNames = new HashMap<>();
        }
        moduleDisplayNames.put(module, displayName);
    }

    public static synchronized String getModuleDisplayName(Module module) {
        if (moduleDisplayNames == null) {
            throw new IllegalStateException();
        }
        String displayName = moduleDisplayNames.get(module);
        Ensure.notNull(displayName);
        return displayName;
    }
}
