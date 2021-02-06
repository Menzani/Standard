package eu.menzani.build;

import eu.menzani.system.Unsafe;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

class JarFileCreator extends SimpleFileVisitor<Path> implements Closeable {
    private static final Map<String, String> env = Map.of("create", "true");

    private final FileSystem fileSystem;

    JarSource source;

    JarFileCreator(String fileToString) throws Exception {
        fileSystem = FileSystems.newFileSystem(URI.create("jar:file:/" + fileToString), env);

        Field readOnly = fileSystem.getClass().getDeclaredField("readOnly");
        Unsafe.setAccessible(readOnly);
        readOnly.set(fileSystem, false);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Files.createDirectories(fileSystem.getPath(source.getPackagePrefix() + source.getPath().relativize(dir)));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String path = source.getPath().relativize(file).toString();
        if (!file.getFileName().toString().equals("module-info.java")) {
            path = source.getPackagePrefix() + path;
        }
        Files.copy(file, fileSystem.getPath(path));
        return FileVisitResult.CONTINUE;
    }

    @Override
    public void close() throws IOException {
        fileSystem.close();
    }
}
