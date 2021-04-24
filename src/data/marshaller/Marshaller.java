package eu.menzani.data.marshaller;

import eu.menzani.data.*;
import eu.menzani.data.Object;
import eu.menzani.object.GarbageCollectionAware;

public abstract class Marshaller {
    public abstract void marshal(Element element, WriteBuffer buffer);

    public abstract Element unmarshal(Source source);

    public void marshal(Element element, Destination destination) {
        marshal(element, new WriteBuffer(destination));
    }

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

        public void appendTo(StringBuilder builder) {
            builder.append(asStringBuilder);
        }

        @Override
        public void gc() {
            asStringBuilder = new StringBuilder();
        }
    }
}
