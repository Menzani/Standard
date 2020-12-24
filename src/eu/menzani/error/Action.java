package eu.menzani.error;

public interface Action {
    FinalAction run(String stackTrace, String stackTraceForFile) throws Throwable;
}
