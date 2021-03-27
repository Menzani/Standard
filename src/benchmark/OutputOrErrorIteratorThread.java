package eu.menzani.benchmark;

import eu.menzani.io.ProcessLauncher;

class OutputOrErrorIteratorThread extends Thread {
    private final ProcessLauncher launcher;
    private final BenchmarkListener listener;
    private final boolean isOutput;

    OutputOrErrorIteratorThread(ProcessLauncher launcher, BenchmarkListener listener, boolean isOutput) {
        this.launcher = launcher;
        this.listener = listener;
        this.isOutput = isOutput;
    }

    @Override
    public void run() {
        doRun();
    }

    void doRun() {
        StringBuilder total = new StringBuilder();
        for (String line : iterator()) {
            total.append(line);
            total.append('\n');

            String totalToString = total.toString();
            if (isOutput) {
                listener.onOutputLineAdded(line);
                listener.updateOutput(totalToString);
            } else {
                listener.onErrorLineAdded(line);
                listener.updateError(totalToString);
            }
        }
        listener.onEnd();
    }

    private Iterable<String> iterator() {
        return isOutput ? launcher.outputIterator() : launcher.errorIterator();
    }
}
