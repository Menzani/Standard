package eu.menzani.lang;

public class Classes {
    public static String getFullName(Class<?> clazz) {
        Module module = clazz.getModule();
        String name = clazz.getName();
        if (module.isNamed()) {
            return module.getName() + '/' + name;
        }
        return name;
    }
}
