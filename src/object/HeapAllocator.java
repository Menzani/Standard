package eu.menzani.object;

import java.util.function.Supplier;

public class HeapAllocator<T> implements Allocator<T> {
    private final Supplier<? extends T> factory;

    public HeapAllocator(Supplier<? extends T> factory) {
        this.factory = factory;
    }

    @Override
    public T allocate() {
        return factory.get();
    }

    @Override
    public void deallocate(T object) {
    }
}
