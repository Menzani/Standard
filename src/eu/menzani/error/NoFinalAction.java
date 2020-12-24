package eu.menzani.error;

public class NoFinalAction implements FinalAction {
    public static final NoFinalAction INSTANCE = new NoFinalAction();

    @Override
    public int getSeverity() {
        return 0;
    }

    @Override
    public void run() {
    }
}
