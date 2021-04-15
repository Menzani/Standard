package eu.menzani.object;

import java.lang.ref.Cleaner;

public class GlobalCleaner {
    private static final Cleaner value = Cleaner.create();

    public static Cleaner.Cleanable register(Object obj, Runnable action) {
        return value.register(obj, action);
    }
}
