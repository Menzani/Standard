package eu.menzani.struct;

public class SimpleBooleanToggle extends BooleanToggle {
    private boolean value;

    @Override
    public boolean toggleTrue() {
        if (value) {
            return false;
        }
        value = true;
        return true;
    }

    @Override
    public boolean toggleFalse() {
        if (!value) {
            return false;
        }
        value = false;
        return true;
    }
}
