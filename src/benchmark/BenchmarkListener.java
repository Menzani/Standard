package eu.menzani.benchmark;

public interface BenchmarkListener {
    void beginProcessCreate();

    void onProcessCreated(Process process);

    void onOutputLineAdded(String line);

    void updateOutput(String output);

    void onErrorLineAdded(String line);

    void updateError(String error);

    void onEnd();
}
