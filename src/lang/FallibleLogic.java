package eu.menzani.lang;

@FunctionalInterface
public interface FallibleLogic {
    void run() throws Throwable;
}
