package eu.menzani.error;

public class ExitFinalAction implements FinalAction {
    public static final ExitFinalAction INSTANCE = new ExitFinalAction();

    @Override
    public int getSeverity() {
        return 1;
    }

    @Override
    public void run() {
        System.exit(-1);
    }
}
