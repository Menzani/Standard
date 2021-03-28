package eu.menzani.object;

import eu.menzani.lang.Lang;
import eu.menzani.lang.UncaughtException;
import eu.menzani.system.ApplicationProperty;

public interface Allocator<T> {
    T allocate();

    void deallocate(T object);

    static <T extends PoolObject> Allocator<T> create(ObjectFactory<T> factory) {
        String allocatorFactoryClassName = new ApplicationProperty(
                factory.newInstance().getClass(), "allocator", "factory").getOrNull();
        if (allocatorFactoryClassName == null) {
            return new HeapAllocator<>(factory);
        }
        Class<? extends ObjectFactory<Allocator<T>>> allocatorFactoryClass = loadAllocatorFactoryClass(allocatorFactoryClassName);
        ObjectFactory<Allocator<T>> allocatorFactory = Lang.newInstance(allocatorFactoryClass);
        return allocatorFactory.newInstance();
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? extends ObjectFactory<Allocator<T>>> loadAllocatorFactoryClass(String name) {
        try {
            return (Class<? extends ObjectFactory<Allocator<T>>>) Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new UncaughtException(e);
        }
    }
}
