package eu.menzani.data.java;

import eu.menzani.data.Marshaller;

public interface ObjectMarshaller<T> {
    void writeTo(Marshaller marshaller, T object);

    T readFrom(Marshaller marshaller);
}
