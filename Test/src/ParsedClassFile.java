package eu.menzani.test;

import eu.menzani.misc.Arrays;
import eu.menzani.system.Unsafe;

import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;

class ParsedClassFile implements Comparable<ParsedClassFile> {
    private final String className;
    private final String superclassName;
    private final String[] interfaceNames;
    private byte[] bytecode;
    private Class<?> loadedClass;

    ParsedClassFile(String className, String superclassName, String[] interfaceNames) {
        this.className = className;
        this.superclassName = superclassName;
        this.interfaceNames = interfaceNames;
    }

    void setBytecode(byte[] bytecode) {
        this.bytecode = bytecode;
    }

    void load(ClassLoader classLoader, ProtectionDomain protectionDomain) {
        loadedClass = Unsafe.defineClass(className, bytecode, classLoader, protectionDomain);
    }

    boolean isNotTestClass() {
        int modifiers = loadedClass.getModifiers();
        return !Modifier.isPublic(modifiers) || Modifier.isAbstract(modifiers) || loadedClass.isInterface() ||
                !className.endsWith("Test") || loadedClass.getDeclaringClass() != null;
    }

    Class<?> getLoadedClass() {
        return loadedClass;
    }

    @Override
    public int compareTo(ParsedClassFile that) {
        if (this == that) {
            return 0;
        }
        if (superclassName.equals(that.className) || Arrays.contains(interfaceNames, that.className)) {
            return 1;
        }
        return -1;
    }
}
