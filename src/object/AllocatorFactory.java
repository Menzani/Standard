package eu.menzani.object;

public interface AllocatorFactory extends Comparable<AllocatorFactory> {
    default int getPriority() {
        return 0;
    }

    boolean shouldBeSelected(Class<?> clazz);

    Allocator<?> newInstance(ObjectFactory<?> factory);

    @Override
    default int compareTo(AllocatorFactory other) {
        return Integer.compare(getPriority(), other.getPriority());
    }
}
