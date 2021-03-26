package eu.menzani.data.json;

import eu.menzani.data.Element;
import eu.menzani.data.Marshaller;

import java.io.Reader;

public class CompactJsonMarshaller extends Marshaller {
    @Override
    public void marshal(Element element, StringBuilder builder) {

    }

    @Override
    public Element unmarshal(Reader reader) {
        return null;
    }
}
