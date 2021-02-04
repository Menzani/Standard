package eu.menzani.lang;

import eu.menzani.system.Unsafe;
import net.bytebuddy.dynamic.loading.ClassInjector;

import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;

public class ByteBuddyClassInjector extends ClassInjector.AbstractBase {
    private final ClassLoader classLoader;
    private final ProtectionDomain protectionDomain;

    public ByteBuddyClassInjector(ClassLoader classLoader) {
        this(classLoader, null);
    }

    public ByteBuddyClassInjector(ClassLoader classLoader, ProtectionDomain protectionDomain) {
        this.classLoader = classLoader;
        this.protectionDomain = protectionDomain;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public Map<String, Class<?>> injectRaw(Map<? extends String, byte[]> types) {
        Map<String, Class<?>> result = new HashMap<>();
        synchronized (classLoader) {
            for (Map.Entry<? extends String, byte[]> entry : types.entrySet()) {
                String name = entry.getKey();
                try {
                    result.put(name, Class.forName(name, false, classLoader));
                } catch (ClassNotFoundException e) {
                    byte[] binaryRepresentation = entry.getValue();
                    result.put(name, Unsafe.defineClass(name, binaryRepresentation, 0, binaryRepresentation.length, classLoader, protectionDomain));
                }
            }
        }
        return result;
    }
}
