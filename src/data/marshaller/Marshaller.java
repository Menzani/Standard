package eu.menzani.data.marshaller;

import eu.menzani.data.Destination;
import eu.menzani.data.Element;
import eu.menzani.data.Object;
import eu.menzani.data.Source;
import eu.menzani.object.GarbageCollectionAware;

public abstract class Marshaller {
    /**
     * Must call {@link Destination#flush()} at the end.
     */
    public abstract void marshal(Element element, Destination destination);

    public abstract Element unmarshal(Source source);

    protected static void requireObject(Element element) {
        if (!(element instanceof Object)) {
            throw new IllegalArgumentException("element must be an Object.");
        }
    }

    protected static class Indent implements GarbageCollectionAware {
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
}
