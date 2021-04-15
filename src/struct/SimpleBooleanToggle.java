package eu.menzani.struct;

public class SimpleBooleanToggle extends BooleanToggle {
    private boolean value;

    @Override
    public boolean setTrue() {
        if (value) {
            return true;
        }
        value = true;
        return false;
    }

    @Override
    public boolean setFalse() {
        if (value) {
            value = false;
            return false;
        }
        return true;
    }

    @Override
    public boolean get() {
        return value;
    }
}
