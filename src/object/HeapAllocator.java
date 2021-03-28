package eu.menzani.object;

public class HeapAllocator<T> implements Allocator<T> {
    private final ObjectFactory<T> factory;

    public HeapAllocator(ObjectFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T allocate() {
        return factory.newInstance();
    }

    @Override
    public void deallocate(T object) {
    }
}
