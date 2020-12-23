package eu.menzani.misc;

import eu.menzani.lang.Optional;
import eu.menzani.lang.UncaughtException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Properties {
    private static final Charset charset = StandardCharsets.UTF_16;

    private final Map<String, String> data = new LinkedHashMap<>();

    void load(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path, charset);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        for (String line : lines) {
            int indexOfEquals = line.indexOf('=');
            if (indexOfEquals == -1) {
                continue;
            }
            data.put(line.substring(0, indexOfEquals), line.substring(indexOfEquals + 1));
        }
    }

    void store(Path path) {
        List<String> lines = new ArrayList<>(data.size());
        for (var entry : data.entrySet()) {
            lines.add(entry.getKey() + '=' + entry.getValue());
        }
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, lines, charset);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    @Optional String getProperty(String key) {
        return data.get(key);
    }

    void setProperty(String key, String value) {
        data.put(key, value);
    }
}
