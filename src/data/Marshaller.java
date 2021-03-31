package eu.menzani.data;

public abstract class Marshaller {
    private static final int defaultBufferSize = 8192;

    public abstract void marshal(Element element, WriteBuffer buffer);

    public abstract Element unmarshal(ReadBuffer buffer);

    public void marshal(Element element, Destination destination) {
        WriteBuffer buffer = new WriteBuffer(defaultBufferSize, destination);
        marshal(element, buffer);
        buffer.flush();
    }

    public Element unmarshal(Source source) {
        return unmarshal(new ReadBuffer(defaultBufferSize, source));
    }

    protected static class Indent {
        private final int increment;

        private int asInt;
        private final StringBuilder asStringBuilder = new StringBuilder();

        public Indent(int increment) {
            this.increment = increment;
            asInt = -increment;
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
    }
}
