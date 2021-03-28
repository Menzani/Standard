package eu.menzani.object;

import eu.menzani.lang.Lang;
import eu.menzani.system.ApplicationProperty;

public interface Allocator<T> {
    T allocate();

    void deallocate(T object);

    static <T> Allocator<T> create(ObjectFactory<T> factory) {
        String allocatorFactoryClassName = new ApplicationProperty(
                factory.newInstance().getClass(), "allocator", "factory").getOrNull();
        if (allocatorFactoryClassName == null) {
            allocatorFactoryClassName = new ApplicationProperty(Allocator.class, "default", "factory").getOrNull();
            if (allocatorFactoryClassName == null) {
                return defaultAllocator(factory);
            }
        }
        Class<? extends ObjectFactory<Allocator<T>>> allocatorFactoryClass;
        try {
            allocatorFactoryClass = loadAllocatorFactoryClass(allocatorFactoryClassName);
        } catch (ClassNotFoundException e) {
            return defaultAllocator(factory);
        }
        ObjectFactory<Allocator<T>> allocatorFactory = Lang.newInstance(allocatorFactoryClass);
        return allocatorFactory.newInstance();
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<? extends ObjectFactory<Allocator<T>>> loadAllocatorFactoryClass(String name)
            throws ClassNotFoundException {
        return (Class<? extends ObjectFactory<Allocator<T>>>) Class.forName(name);
    }

    private static <T> Allocator<T> defaultAllocator(ObjectFactory<T> factory) {
        return new HeapAllocator<>(factory);
    }
}
