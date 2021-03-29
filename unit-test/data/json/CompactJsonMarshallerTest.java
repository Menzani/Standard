package eu.menzani.data.json;

import eu.menzani.data.Boolean;
import eu.menzani.data.Integer;
import eu.menzani.data.Object;
import eu.menzani.data.*;
import eu.menzani.lang.Assert;

import java.lang.String;

public class CompactJsonMarshallerTest {
    private static final Object element1;
    private static final String string1 = "{\"key1\":\"value1\",\"key2\":[true,false,null,55,55.78,[],{\"key3\":{}}]}";

    static {
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
    }

    private final Marshaller marshaller = new CompactJsonMarshaller();

    public void marshal() {
        marshal(Boolean.TRUE, "true");
        marshal(Boolean.FALSE, "false");
        marshal(null, "null");
        marshal(Integer.allocate((short) 7), "7");
        marshal(Integer.allocate(7), "7");
        marshal(Integer.allocate(7L), "7");
        marshal(Decimal.allocate(5.5D), "5.5");
        marshal(Decimal.allocate(5.5F), "5.5");
        marshal(eu.menzani.data.String.allocate(""), "\"\"");
        marshal(Array.allocate(), "[]");
        marshal(Object.allocate(), "{}");
        marshal(element1, string1);
    }

    public void unmarshal() {
        unmarshal("true", Boolean.TRUE);
        unmarshal("false", Boolean.FALSE);
        unmarshal("null", null);
        unmarshal("7", Integer.allocate(7L));
        unmarshal("5.5", Decimal.allocate(5.5D));
        unmarshal("\"\"", eu.menzani.data.String.allocate(""));
        unmarshal("[]", Array.allocate());
        unmarshal("{}", Object.allocate());
        unmarshal(string1, element1);
    }

    private void marshal(Element from, String to) {
        CharSequenceDestination destination = new CharSequenceDestination();
        marshaller.marshal(from, destination);
        Assert.equal(destination.getString(), to);
    }

    private void unmarshal(String from, Element to) {
        Source source = new CharSequenceSource(from);
        Element element = marshaller.unmarshal(source);
        Assert.equal(element, to);
    }
}
