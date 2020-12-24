package eu.menzani.error;

public class HaltFinalAction implements FinalAction {
    public static final HaltFinalAction INSTANCE = new HaltFinalAction();

    @Override
    public int getSeverity() {
        return 2;
    }

    @Override
    public void run() {
        Runtime.getRuntime().halt(-1);
    }
}
