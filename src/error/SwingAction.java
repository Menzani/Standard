package eu.menzani.error;

public interface SwingAction {
    void run(String stackTrace, String stackTraceForLabel) throws Throwable;
}
