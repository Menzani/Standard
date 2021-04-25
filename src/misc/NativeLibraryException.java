package eu.menzani.misc;

import eu.menzani.io.PrintToConsoleException;
import eu.menzani.lang.Modules;

public class NativeLibraryException extends PrintToConsoleException {
    private static final long serialVersionUID = 0L;

    private final Module module;

    NativeLibraryException(Module module) {
        super("Could not compile native library for module " + Modules.getPrettyName(module) + '.');
        this.module = module;
    }

    public Module getModule() {
        return module;
    }
}
