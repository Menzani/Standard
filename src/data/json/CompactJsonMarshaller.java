package eu.menzani.data.json;

import eu.menzani.data.Boolean;
import eu.menzani.data.Integer;
import eu.menzani.data.Object;
import eu.menzani.data.String;
import eu.menzani.data.*;

import java.io.Reader;
import java.util.Map;

public class CompactJsonMarshaller implements Marshaller {
    @Override
    public void marshal(Element element, StringBuilder builder) {
        if (element == null) {
            builder.append((java.lang.String) null);
        } else if (element instanceof String) {
            builder.append('"');
            builder.append(((String) element).asCharSequence());
            builder.append('"');
        } else if (element instanceof Integer) {
            builder.append(((Integer) element).asLong());
        } else if (element instanceof Object) {
            builder.append('{');
            for (Map.Entry<java.lang.String, Element> keyWithValue : ((Object) element).getKeysWithValues()) {
                builder.append('"');
                builder.append(keyWithValue.getKey());
                builder.append("\":");
                marshal(keyWithValue.getValue(), builder);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
            builder.append('}');
        } else if (element instanceof Array) {
            builder.append('[');
            for (Element arrayElement : ((Array) element).asList()) {
                marshal(arrayElement, builder);
                builder.append(',');
            }
            builder.setLength(builder.length() - 1);
            builder.append(']');
        } else if (element instanceof Decimal) {
            builder.append(((Decimal) element).asDouble());
        } else if (element instanceof Boolean) {
            builder.append(((Boolean) element).asPrimitive());
        }
    }

    @Override
    public Element unmarshal(Reader reader) {
        return null;
    }
}
