package eu.menzani.misc;

public class NativeLibraryException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    private final Module module;

    NativeLibraryException(String message, Module module) {
        super(message + " native library for module " + new eu.menzani.lang.Module(module).getPrettyName() + '.');
        this.module = module;
    }

    public Module getModule() {
        return module;
    }
}
