package eu.menzani.data.marshaller;

import eu.menzani.data.Destination;
import eu.menzani.data.Element;
import eu.menzani.data.Object;
import eu.menzani.data.Source;

public abstract class Marshaller {
    /**
     * Must call {@link Destination#flush()} at the end.
     */
    public abstract void marshal(Element element, Destination destination);

    public abstract Element unmarshal(Source source);

    protected static void requireObject(Element element) {
        if (!(element instanceof Object)) {
            throw new IllegalArgumentException("element must be an Object.");
        }
    }
}
