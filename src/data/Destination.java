package eu.menzani.data;

import eu.menzani.lang.TargetReplacement;

public abstract class Destination {
    private final StringBuilder longBuffer = new StringBuilder(24);

    private boolean shouldReplace;
    private TargetReplacement[] targetReplacements;

    public void setCurrentTargetReplacements(TargetReplacement[] targetReplacements) {
        this.targetReplacements = targetReplacements;
        shouldReplace = true;
    }

    public void removeCurrentTargetReplacements() {
        shouldReplace = false;
    }

    protected boolean shouldReplace() {
        return shouldReplace;
    }

    protected TargetReplacement[] getTargetReplacements() {
        return targetReplacements;
    }

    public abstract void append(char character);

    public abstract void append(java.lang.String string);

    public abstract void append(StringBuilder builder);

    public void append(long l) {
        longBuffer.setLength(0);
        longBuffer.append(l);
        append(longBuffer);
    }

    public abstract void deleteLastChar();

    public void checkFlush() {
    }

    public void flush() {
    }
}
