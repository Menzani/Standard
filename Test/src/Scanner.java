package eu.menzani.test;

import eu.menzani.collection.ListBuilder;
import eu.menzani.system.Unsafe;
import org.apache.bcel.classfile.ClassParser;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

class Scanner extends SimpleFileVisitor<Path> {
    private final SortedSet<ParsedClassFile> classFiles = new TreeSet<>();
    private List<LoadedClass> testClasses;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        ClassParser parser = new ClassParser(Files.newInputStream(file), null);
        classFiles.add(new ParsedClassFile(parser.parse(), file));
        return FileVisitResult.CONTINUE;
    }

    void loadClasses(ClassLoader classLoader, ProtectionDomain protectionDomain) throws IOException {
        ListBuilder<ParsedClassFile, LoadedClass> testClassesBuilder = new ListBuilder<>(classFiles);
        for (ParsedClassFile classFile : testClassesBuilder) {
            String className = classFile.getClassName();
            Class<?> loaded = Unsafe.defineClass(className, classFile.readBytecode(), classLoader, protectionDomain);
            if (classFile.isTestClass()) {
                testClassesBuilder.add(new LoadedClass(loaded, className));
            }
        }
        testClasses = testClassesBuilder.build();
    }

    Collection<LoadedClass> getTestClasses() {
        return testClasses;
    }

    static class LoadedClass {
        final Class<?> loaded;
        final String name;

        private LoadedClass(Class<?> loaded, String name) {
            this.loaded = loaded;
            this.name = name;
        }
    }
}
