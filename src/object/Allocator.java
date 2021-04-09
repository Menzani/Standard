package eu.menzani.object;

public interface Allocator<T> {
    T allocate();

    void deallocate(T object);

    static <T> Allocator<T> create(ObjectFactory<T> factory) {
        Class<?> clazz = factory.getObjectClass();
        for (AllocatorFactory allocatorFactory : AllocatorFactoryRegistry.FACTORIES) {
            if (allocatorFactory.shouldBeSelected(clazz)) {
                @SuppressWarnings("unchecked") var allocator = (Allocator<T>) allocatorFactory.newInstance(factory);
                return allocator;
            }
        }
        return new HeapAllocator<>(factory);
    }

    static void addFactory(AllocatorFactory factory) {
        AllocatorFactoryRegistry.FACTORIES.add(factory);
    }

    static void addFactories(Iterable<? extends AllocatorFactory> factories) {
        for (AllocatorFactory factory : factories) {
            addFactory(factory);
        }
    }

    static void addFactories(AllocatorFactory... factories) {
        for (AllocatorFactory factory : factories) {
            addFactory(factory);
        }
    }
}
