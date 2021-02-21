package eu.menzani.misc;

import eu.menzani.lang.Optional;

public class NativeLibraryException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    private final Module module;

    NativeLibraryException(String message, @Optional Throwable cause, @Optional Module module) {
        super(createMessage(message, module), cause);
        this.module = module;
    }

    private static String createMessage(String message, Module module) {
        message += " native library";
        if (module != null) {
            message += " for module " + new eu.menzani.lang.Module(module).getPrettyName();
        }
        return message + '.';
    }

    public Module getModule() {
        return module;
    }
}
