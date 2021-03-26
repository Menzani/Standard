package eu.menzani.object;

public interface Allocator<T> {
    T allocate();

    void deallocate(T object);
}
