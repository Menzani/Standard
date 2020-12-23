package eu.menzani.misc;

import java.nio.file.Files;
import java.nio.file.Path;

public class PropertyConfiguration {
    private final Path file;
    private final Properties properties = new Properties();
    private final Properties defaultProperties = new Properties();

    public PropertyConfiguration(Path file) {
        if (Files.exists(file)) {
            properties.load(file);
        }
        this.file = file;
    }

    public String getString(String key, String defaultValue) {
        defaultProperties.setProperty(key, defaultValue);
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public int getInt(String key, int defaultValue) {
        defaultProperties.setProperty(key, Integer.toString(defaultValue));
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public int getInt(String key, int defaultValue, String defaultPropertyValue) {
        defaultProperties.setProperty(key, defaultPropertyValue);
        String value = properties.getProperty(key);
        if (value == null || value.equals(defaultPropertyValue)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public Path getPath(String key, Path defaultValue) {
        defaultProperties.setProperty(key, defaultValue.toString());
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return Path.of(value);
    }

    public void saveDefault() {
        if (Files.notExists(file)) {
            defaultProperties.store(file);
        }
    }
}
