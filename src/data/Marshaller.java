package eu.menzani.data;

import java.io.Reader;

public interface Marshaller {
    void marshal(Element element, StringBuilder builder);

    Element unmarshal(Reader reader);
}
