package eu.menzani.lang;

import java.util.function.Predicate;

@FunctionalInterface
public interface Filter<T> extends Predicate<T> {
    boolean shouldRemove(T element) throws Exception;

    @Override
    default boolean test(T t) {
        try {
            return shouldRemove(t);
        } catch (Exception e) {
            throw new UncaughtException(e);
        }
    }
}
