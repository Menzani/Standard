package eu.menzani.system;

import eu.menzani.lang.UncaughtException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ExtensibleClassLoader extends ClassLoader {
    private final Map<String, byte[]> loadedClasses = new HashMap<>();

    public ExtensibleClassLoader() {
        super();
    }

    public ExtensibleClassLoader(ClassLoader parent) {
        super(parent);
    }

    public ExtensibleClassLoader(String name, ClassLoader parent) {
        super(name, parent);
    }

    public Set<String> getLoadedClassNames() {
        return loadedClasses.keySet();
    }

    public void add(String className, Path classFile) {
        byte[] bytecode;
        try {
            bytecode = Files.readAllBytes(classFile);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        loadedClasses.put(className, bytecode);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytecode = loadedClasses.get(name);
        if (bytecode == null) {
            throw new ClassNotFoundException(name);
        }
        return defineClass(name, bytecode, 0, bytecode.length);
    }
}
