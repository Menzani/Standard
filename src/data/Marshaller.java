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
}
