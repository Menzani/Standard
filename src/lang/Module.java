package eu.menzani.lang;

public class Module {
    private final java.lang.Module module;

    public static Module of(Class<?> clazz) {
        return new Module(clazz.getModule());
    }

    public Module(java.lang.Module module) {
        this.module = module;
    }

    public java.lang.Module getJavaObject() {
        return module;
    }

    public String getPrettyName() {
        String name = module.getName();
        if (name == null) {
            return "unnamed";
        }
        return name;
    }

    public String getNameEmptyOrWithSlash() {
        String name = module.getName();
        if (name == null) {
            return "";
        }
        return name + '/';
    }
}
