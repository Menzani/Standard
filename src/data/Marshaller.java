package eu.menzani.data;

import eu.menzani.lang.WriterBuffer;

import java.io.Reader;

public abstract class Marshaller {
    private final WriterBuffer buffer = null;

    public abstract void marshal(Element element, StringBuilder builder);

    protected void checkAutoFlush() {
        buffer.checkAutoFlush();
    }

    public abstract Element unmarshal(Reader reader);
}
