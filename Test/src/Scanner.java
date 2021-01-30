package eu.menzani.test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.ProtectionDomain;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class Scanner extends SimpleFileVisitor<Path> {
    private final ClassParser parser = new ClassParser();
    private final SortedSet<ParsedClassFile> classFiles = new TreeSet<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        byte[] bytecode = Files.readAllBytes(file);
        classFiles.add(parser.parse(bytecode));
        return FileVisitResult.CONTINUE;
    }

    void loadClasses(ClassLoader classLoader, ProtectionDomain protectionDomain) {
        Iterator<ParsedClassFile> iterator = classFiles.iterator();
        while (iterator.hasNext()) {
            ParsedClassFile classFile = iterator.next();
            classFile.load(classLoader, protectionDomain);
            if (classFile.isNotTestClass()) {
                iterator.remove();
            }
        }
    }

    Set<ParsedClassFile> getTestClasses() {
        return classFiles;
    }
}
