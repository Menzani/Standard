package eu.menzani.lang;

import java.util.function.Function;

@FunctionalInterface
public interface Mapper<T, R> extends Function<T, R> {
    R map(T element) throws Exception;

    @Override
    default R apply(T t) {
        try {
            return map(t);
        } catch (Exception e) {
            throw new UncaughtException(e);
        }
    }
}
