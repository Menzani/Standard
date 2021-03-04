package eu.menzani.error;

public interface HandledThrowable {
    void run();

    default boolean shouldBeIgnored() {
        return false;
    }
}
