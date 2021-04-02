package eu.menzani.lang;

public class Modules {
    public static String getPrettyName(Module module) {
        String name = module.getName();
        if (name == null) {
            return "unnamed";
        }
        return name;
    }
}
