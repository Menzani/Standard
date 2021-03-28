package eu.menzani.data;

import eu.menzani.lang.CharBuffer;

public interface Marshaller {
    void marshal(Element element, StringBuilder builder);

    Element unmarshal(CharBuffer buffer);
}
