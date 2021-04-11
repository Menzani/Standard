package eu.menzani.data.marshaller;

import eu.menzani.data.Boolean;
import eu.menzani.data.Integer;
import eu.menzani.data.Object;
import eu.menzani.data.*;

import java.lang.String;

public class YamlMarshallerTest extends MarshallerTest {
    private static final String string1 = "key1: value1\nkey2:\n  - true\n  - false\n  - null\n  - 55\n  - 55.78\n  - []\n  - key3: {}\nkey3: \r\n";

    public YamlMarshallerTest() {
        super(new YamlMarshaller("\n"));
    }

    public void marshal() {
        marshal(Boolean.TRUE, "true");
        marshal(Boolean.FALSE, "false");
        marshal(null, "null");
        marshal(Integer.allocate((short) 7), "7");
        marshal(Integer.allocate(7), "7");
        marshal(Integer.allocate(7L), "7");
        marshal(Decimal.allocate(5.5D), "5.5");
        marshal(Decimal.allocate(5.5F), "5.5");
        marshal(eu.menzani.data.String.allocate(""), "");
        marshal(eu.menzani.data.String.allocate(":-\\"), ":-\\");
        marshal(Array.allocate(), "[]");
        marshal(Object.allocate(), "{}");
        marshal(element1, string1);
    }
}
