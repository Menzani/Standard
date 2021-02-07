package eu.menzani.lang;

public class EndVisit extends ControlFlowException {
    public static final EndVisit INSTANCE = new EndVisit();

    private static final long serialVersionUID = 0L;

    private EndVisit() {
    }
}
