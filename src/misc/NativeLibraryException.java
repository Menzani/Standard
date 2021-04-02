package eu.menzani.misc;

import eu.menzani.lang.Modules;

public class NativeLibraryException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    private final Module module;

    NativeLibraryException(String message, Module module) {
        super(message + " native library for module " + Modules.getPrettyName(module) + '.');
        this.module = module;
    }

    public Module getModule() {
        return module;
    }
}
