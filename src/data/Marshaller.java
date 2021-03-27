package eu.menzani.data;

import java.io.Reader;

public abstract class Marshaller {
    public abstract void marshal(Element element, StringBuilder builder);

    public abstract Element unmarshal(Reader reader);
}
