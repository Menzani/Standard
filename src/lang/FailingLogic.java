package eu.menzani.lang;

@FunctionalInterface
public interface FailingLogic {
    void run() throws Throwable;
}
