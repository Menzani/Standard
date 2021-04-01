package eu.menzani.data.yaml;

import eu.menzani.data.Boolean;
import eu.menzani.data.Integer;
import eu.menzani.data.Object;
import eu.menzani.data.String;
import eu.menzani.data.*;
import eu.menzani.lang.StringBuilders;
import eu.menzani.lang.TargetReplacement;
import eu.menzani.struct.Strings;

import java.util.Map;
import java.util.Set;

public class YamlMarshaller extends Marshaller {
    private static final TargetReplacement[] stringEscapes = {new TargetReplacement('\\', "\\\\"), new TargetReplacement(':', "\\:")};

    private final java.lang.String ln;

    public YamlMarshaller() {
        this(Strings.LN);
    }

    public YamlMarshaller(java.lang.String lineSeparator) {
        ln = lineSeparator;
    }

    @Override
    public void marshal(Element element, WriteBuffer buffer) {
        marshal(element, buffer, buffer.builder, new Indent(2), false, true);
    }

    private void marshal(Element element, WriteBuffer buffer, StringBuilder builder, Indent indent, boolean isValueOfKey, boolean isNotElementOfArray) {
        if (element == null) {
            if (isValueOfKey) {
                builder.append(' ');
            }
            builder.append((java.lang.String) null);
        } else if (element instanceof String) {
            if (isValueOfKey) {
                builder.append(' ');
            }
            builder.append(((String) element).asCharSequence());
        } else if (element instanceof Integer) {
            if (isValueOfKey) {
                builder.append(' ');
            }
            builder.append(((Integer) element).asLong());
        } else if (element instanceof Object) {
            Set<Map.Entry<java.lang.String, Element>> keysWithValues = ((Object) element).getKeysWithValues();
            if (keysWithValues.isEmpty()) {
                if (isValueOfKey) {
                    builder.append(' ');
                }
                builder.append("{}");
            } else {
                indent.increment();
                boolean isFirst = true;
                for (Map.Entry<java.lang.String, Element> keyWithValue : keysWithValues) {
                    if (isFirst) {
                        if (isNotElementOfArray) {
                            indent.appendTo(builder);
                        }
                        isFirst = false;
                    } else {
                        builder.append(ln);
                        indent.appendTo(builder);
                    }
                    int start = buffer.position();
                    builder.append(keyWithValue.getKey());
                    StringBuilders.replace(builder, start, buffer.position(), stringEscapes);
                    builder.append(':');
                    buffer.checkFull();
                    marshal(keyWithValue.getValue(), buffer, builder, indent, true, true);
                }
                indent.decrement();
            }
        } else if (element instanceof Array) {
            Array array = (Array) element;
            if (array.isEmpty()) {
                if (isValueOfKey) {
                    builder.append(' ');
                }
                builder.append("[]");
            } else {
                indent.increment();
                boolean isFirst = true;
                for (Element arrayElement : array) {
                    if (isFirst) {
                        if (indent.wasIncrementedAtLeastTwice()) {
                            builder.append(ln);
                        }
                        isFirst = false;
                    } else {
                        builder.append(ln);
                    }
                    indent.appendTo(builder);
                    builder.append("- ");
                    buffer.checkFull();
                    marshal(arrayElement, buffer, builder, indent, false, false);
                }
                indent.decrement();
            }
        } else if (element instanceof Decimal) {
            if (isValueOfKey) {
                builder.append(' ');
            }
            builder.append(((Decimal) element).asDouble());
        } else if (element instanceof Boolean) {
            if (isValueOfKey) {
                builder.append(' ');
            }
            builder.append(((Boolean) element).asPrimitive());
        } else {
            throw new AssertionError();
        }
    }

    @Override
    public Element unmarshal(ReadBuffer buffer) {
        return null;
    }
}
