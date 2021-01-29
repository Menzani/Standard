package eu.menzani.system;

import eu.menzani.collection.SetBuilder;
import eu.menzani.lang.Optional;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

public class SystemProperty extends RuntimeProperty {
    public SystemProperty(String first, String... more) {
        this(null, first, more);
    }

    private SystemProperty(String initialValue, String first, String[] more) {
        super(initialValue, first, more);
    }

    public SystemProperty resolve(String first, String... more) {
        return new SystemProperty(key, first, more);
    }

    @Override
    public SystemProperty set(String value) {
        super.set(value);
        return this;
    }

    @Override
    public SystemProperty setOrRemove(String value) {
        super.setOrRemove(value);
        return this;
    }

    @Override
    public SystemProperty setAsBoolean(boolean value) {
        super.setAsBoolean(value);
        return this;
    }

    @Override
    public SystemProperty remove() {
        super.remove();
        return this;
    }

    public static final Set<Path> CLASS_PATH = mapToPath(new SystemProperty("java", "class", "path").get());
    public static final Set<Path> MODULE_PATH = mapToPath(new SystemProperty("jdk", "module", "path").getOrNull());

    private static Set<Path> mapToPath(@Optional String string) {
        if (string == null) {
            return Collections.emptySet();
        }
        SetBuilder<String, Path> builder = new SetBuilder<>(string.split(";"));
        for (String path : builder) {
            builder.add(Path.of(path));
        }
        return builder.buildUnmodifiable();
    }
}
