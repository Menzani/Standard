package eu.menzani.test;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

class Scanner extends SimpleFileVisitor<Path> {
    private final List<String> classNames = new ArrayList<>();
    private int startIndex;

    List<String> getClassNames() {
        return classNames;
    }

    void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String fileToString = file.toString();
        if (fileToString.endsWith("Test.class")) {
            String className = fileToString.substring(startIndex, fileToString.length() - 6);
            classNames.add(className.replace(File.separatorChar, '.'));
        }
        return FileVisitResult.CONTINUE;
    }
}
