package eu.menzani.misc;

import eu.menzani.lang.ControlFlowException;

public class EndVisit extends ControlFlowException {
    public static final EndVisit INSTANCE = new EndVisit();

    private static final long serialVersionUID = 0L;

    private EndVisit() {
    }
}
