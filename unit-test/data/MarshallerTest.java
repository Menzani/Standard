package eu.menzani.data;

import eu.menzani.lang.Assert;

public abstract class MarshallerTest {
    protected final Object element1;

    {
        element1 = Object.allocate();
        element1.set("key1", "value1");
        Array value2 = Array.allocate();
        value2.add(true);
        value2.add(false);
        value2.addNull();
        value2.add(55);
        value2.add(55.78D);
        value2.add(Array.allocate());
        Object object = Object.allocate();
        object.set("key3", Object.allocate());
        value2.add(object);
        element1.set("key2", value2);
        element1.set("key3", "\r\n");
    }

    private final Marshaller marshaller;

    protected MarshallerTest(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    protected void marshal(Element from, java.lang.String to) {
        CharSequenceDestination destination = new CharSequenceDestination();
        marshaller.marshal(from, destination);
        Assert.equalToMultiline(destination.getString(), to);
    }

    protected void unmarshal(java.lang.String from, Element to) {
        Source source = new CharSequenceSource(from);
        Element element = marshaller.unmarshal(source);
        Assert.equalToMultiline(element, to);
    }
}
