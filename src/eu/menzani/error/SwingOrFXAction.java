package eu.menzani.error;

public interface SwingOrFXAction {
    void run(String stackTrace, String stackTraceForLabel) throws Throwable;
}
