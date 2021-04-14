package eu.menzani.data.marshaller;

import eu.menzani.data.Boolean;
import eu.menzani.data.Integer;
import eu.menzani.data.Object;
import eu.menzani.data.String;
import eu.menzani.data.*;

public class CompactJsonMarshallerTest extends MarshallerTest {
    private static final java.lang.String string1 = "{\"key1\":\"value1\",\"key2\":[true,false,null,55,55.78,[],{\"key3\":{}}],\"key3\":\"\r\n\"}";

    public CompactJsonMarshallerTest() {
        super(new CompactJsonMarshaller());
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
        marshal(String.allocate(""), "\"\"");
        marshal(String.allocate("\"-\\"), "\"\\\"-\\\\\"");
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
        unmarshal("\"\"", String.allocate(""));
        unmarshal("\"\\\"-\\\\\"", String.allocate("\"-\\"));
        unmarshal("[]", Array.allocate());
        unmarshal("{}", Object.allocate());
        unmarshal(string1, element1);
    }
}
