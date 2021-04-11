package eu.menzani.data.marshaller;

import eu.menzani.data.Element;

public interface ObjectMarshaller<T> {
    Element toElement(T object);

    T fromElement(Element element);
}
