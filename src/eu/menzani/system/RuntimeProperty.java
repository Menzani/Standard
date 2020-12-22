package eu.menzani.system;

import eu.menzani.lang.Check;
import eu.menzani.lang.Optional;

import java.nio.file.Path;
import java.util.function.Supplier;

abstract class RuntimeProperty {
    final String key;

    RuntimeProperty(@Optional String initialValue, String first, String[] more) {
        StringBuilder builder = new StringBuilder();
        if (initialValue != null) {
            builder.append(initialValue);
            builder.append('.');
        }
        builder.append(first);
        for (String segment : more) {
            builder.append('.');
            builder.append(segment);
        }
        key = builder.toString();
    }

    public String get() {
        String value = getOrNull();
        Check.notNull(value);
        return value;
    }

    public @Optional String getOrNull() {
        return System.getProperty(key);
    }

    public boolean getAsBoolean() {
        return getOrNull() != null;
    }

    public Path getAsPath() {
        return Path.of(get());
    }

    public @Optional Path getAsPathOrNull() {
        String value = getOrNull();
        if (value == null) {
            return null;
        }
        return Path.of(value);
    }

    public Path getAsPathOr(Supplier<? extends Path> defaultValue) {
        String value = getOrNull();
        if (value == null) {
            return defaultValue.get();
        }
        return Path.of(value);
    }

    public abstract String toString();
}
