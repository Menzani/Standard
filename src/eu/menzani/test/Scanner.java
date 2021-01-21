package eu.menzani.test;

import eu.menzani.system.ExtensibleClassLoader;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class Scanner extends SimpleFileVisitor<Path> {
    private final ExtensibleClassLoader classLoader = new ExtensibleClassLoader();
    private int startIndex;

    ExtensibleClassLoader getClassLoader() {
        return classLoader;
    }

    void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String fileToString = file.toString();
        if (fileToString.endsWith("Test.class")) {
            String className = fileToString.substring(startIndex, fileToString.length() - 6).replace(File.separatorChar, '.');
            classLoader.add(className, file);
        }
        return FileVisitResult.CONTINUE;
    }
}
