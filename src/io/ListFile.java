package eu.menzani.io;

import eu.menzani.struct.Patterns;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class ListFile implements Closeable {
    private static final String[] noLines = new String[0];

    private final Path file;
    private final Charset charset;
    private final OpenOption[] writeOptions;

    private boolean deleteOnEmpty;
    private boolean fillOnRead;

    public final StringBuilder builder = new StringBuilder();
    private String[] lines;

    public ListFile(Path folder, String name) {
        this(folder, name, StandardCharsets.UTF_8);
    }

    public ListFile(Path folder, String name, Charset charset, OpenOption... writeOptions) {
        this(folder.resolve(name + ".lst"), charset, writeOptions);
    }

    public ListFile(Path file) {
        this(file, StandardCharsets.UTF_8);
    }

    public ListFile(Path file, Charset charset, OpenOption... writeOptions) {
        this.file = file;
        this.charset = charset;
        this.writeOptions = writeOptions;
    }

    public ListFile deleteOnEmpty() {
        deleteOnEmpty = true;
        return this;
    }

    public ListFile fillOnRead() {
        fillOnRead = true;
        return this;
    }

    public void next() {
        builder.append(System.lineSeparator());
    }

    @Override
    public void close() throws IOException {
        if (deleteOnEmpty && builder.length() == 0) {
            Files.deleteIfExists(file);
            return;
        }
        Files.writeString(file, builder, charset, writeOptions);
    }

    public void read() throws IOException {
        if (Files.exists(file)) {
            String content = Files.readString(file, charset);
            lines = Patterns.SPLIT_BY_NEWLINE.split(content);
        } else {
            lines = noLines;
        }
        if (fillOnRead) {
            if (builder.length() != 0) {
                throw new IllegalStateException("Some contents have already been written.");
            }
            for (String line : lines) {
                builder.append(line);
                next();
            }
        }
    }

    public boolean isEmpty() {
        return lines.length == 0;
    }

    public boolean isNotEmpty() {
        return lines.length != 0;
    }

    public String[] lines() {
        return lines;
    }

    public boolean contains(String line) {
        for (String _line : lines) {
            if (_line.equals(line)) {
                return true;
            }
        }
        return false;
    }
}
