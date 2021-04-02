package eu.menzani.lang;

public class Classes {
    public static String getFullName(Class<?> clazz) {
        Module module = clazz.getModule();
        String result = clazz.getName();
        if (module.isNamed()) {
            result = module.getName() + '/' + result;
        }
        return result;
    }
}
