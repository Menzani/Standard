package eu.menzani.system;

import eu.menzani.lang.ListBuilder;

import java.nio.file.Path;
import java.util.List;

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

    public static final List<Path> CLASS_PATH = mapToPath(new SystemProperty("java", "class", "path").get());
    public static final List<Path> MODULE_PATH = mapToPath(new SystemProperty("jdk", "module", "path").get());

    private static List<Path> mapToPath(String string) {
        ListBuilder<String, Path> listBuilder = new ListBuilder<>(string.split(";"));
        listBuilder.map(Path::of);
        return listBuilder.buildUnmodifiable();
    }
}
