package eu.menzani.data.marshaller;

import eu.menzani.data.Element;

public interface ObjectMarshaller<T> {
    Element marshal(T object);

    T unmarshal(Element element);
}
