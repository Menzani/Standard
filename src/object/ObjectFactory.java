package eu.menzani.object;

@FunctionalInterface
public interface ObjectFactory<T> {
    T newInstance();

    @SuppressWarnings("unchecked")
    default Class<T> getObjectClass() {
        return (Class<T>) newInstance().getClass();
    }
}
