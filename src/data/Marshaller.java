package eu.menzani.data;

public abstract class Marshaller {
    private static final int defaultBufferSize = 8192;

    public abstract void marshal(Element element, WriteBuffer buffer);

    public abstract Element unmarshal(ReadBuffer buffer);

    public void marshal(Element element, Destination destination) {
        marshal(element, new WriteBuffer(defaultBufferSize, destination));
    }

    public Element unmarshal(Source source) {
        return unmarshal(new ReadBuffer(defaultBufferSize, source));
    }
}
