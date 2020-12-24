package eu.menzani.error;

public interface FinalAction {
    int getSeverity();

    void run() throws Throwable;
}
