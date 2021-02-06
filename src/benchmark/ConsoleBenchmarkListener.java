package eu.menzani.benchmark;

class ConsoleBenchmarkListener implements BenchmarkListener {
    static final ConsoleBenchmarkListener INSTANCE = new ConsoleBenchmarkListener();

    @Override
    public void beginProcessCreate() {
    }

    @Override
    public void onProcessCreated(Process process) {
    }

    @Override
    public void onOutputLineAdded(String line) {
        System.out.println(line);
    }

    @Override
    public void updateOutput(String output) {
    }

    @Override
    public void onEnd() {
    }
}
