package eu.menzani.data.marshaller;

import eu.menzani.data.Destination;
import eu.menzani.object.GarbageCollectionAware;

public class Indent implements GarbageCollectionAware {
    private final int increment;

    private int asInt;
    private StringBuilder asStringBuilder;

    public Indent(int increment) {
        this.increment = increment;
        gc();
        reset();
    }

    public void reset() {
        asInt = -increment;
        asStringBuilder.setLength(0);
    }

    public boolean wasIncrementedAtLeastTwice() {
        return asInt > 0;
    }

    public void increment() {
        if (asInt >= 0) {
            for (int i = 0; i < increment; i++) {
                asStringBuilder.append(' ');
            }
        }
        asInt += increment;
    }

    public void decrement() {
        asInt -= increment;
        if (asInt >= 0) {
            asStringBuilder.setLength(asInt);
        }
    }

    public void appendTo(Destination destination) {
        destination.append(asStringBuilder);
    }

    @Override
    public void gc() {
        asStringBuilder = new StringBuilder();
    }
}
