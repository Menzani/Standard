package eu.menzani.data.marshaller;

public class YamlMarshallerTest extends MarshallerTest {
    private static final String string1 = "key1: value1\nkey2:\n  - true\n  - false\n  - null\n  - 55\n  - 55.78\n  - []\n  - key3: {}\nkey3: \r\n";

    public YamlMarshallerTest() {
        super(new YamlMarshaller("\n"));
    }

    public void marshal() {
        marshal(element1, string1);
    }
}
