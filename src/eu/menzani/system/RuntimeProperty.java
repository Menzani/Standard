package eu.menzani.system;

import eu.menzani.lang.Assume;
import eu.menzani.lang.Check;
import eu.menzani.lang.Optional;

import java.nio.file.Path;
import java.util.function.Supplier;

public abstract class RuntimeProperty {
    final String key;
    private String value;

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

    public RuntimeProperty set(String value) {
        Assume.notNull(value);
        this.value = value;
        return this;
    }

    public RuntimeProperty setOrRemove(String value) {
        this.value = value;
        return this;
    }

    public RuntimeProperty setAsBoolean(boolean value) {
        if (value) {
            set("");
        } else {
            remove();
        }
        return this;
    }

    public RuntimeProperty remove() {
        value = null;
        return this;
    }

    public void update() {
        if (value == null) {
            System.clearProperty(key);
        } else {
            System.setProperty(key, value);
        }
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        String result = " -D" + key;
        if (!value.equals("")) {
            result += '=' + value;
        }
        return result;
    }
}
