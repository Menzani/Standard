package eu.menzani.object;

public interface AllocatorFactory<T> extends Comparable<AllocatorFactory<?>> {
    default int getPriority() {
        return 0;
    }

    boolean shouldBeSelected(Class<?> clazz);

    Allocator<T> newInstance(ObjectFactory<T> factory);

    @Override
    default int compareTo(AllocatorFactory<?> other) {
        return Integer.compare(getPriority(), other.getPriority());
    }
}
