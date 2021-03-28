package eu.menzani.object;

@FunctionalInterface
public interface ObjectFactory<T> {
    T newInstance();
}
