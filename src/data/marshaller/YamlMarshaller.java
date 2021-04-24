package eu.menzani.data.marshaller;

import eu.menzani.data.Boolean;
import eu.menzani.data.Integer;
import eu.menzani.data.Object;
import eu.menzani.data.String;
import eu.menzani.data.*;
import eu.menzani.lang.DecimalConversion;
import eu.menzani.lang.TargetReplacement;
import eu.menzani.object.GarbageCollectionAware;
import eu.menzani.struct.Strings;

public class YamlMarshaller extends Marshaller implements GarbageCollectionAware {
    private static final TargetReplacement[] stringEscapes = {new TargetReplacement('\\', "\\\\"), new TargetReplacement(':', "\\:")};

    private final java.lang.String ln;
    private Indent indent;

    private final DecimalConversion decimalConversion = new DecimalConversion();
    private StringBuilder keyBuilder;

    public YamlMarshaller() {
        this(Strings.LN);
    }

    public YamlMarshaller(java.lang.String lineSeparator) {
        ln = lineSeparator;
        setIndent(2);
        gc();
    }

    public void setIndent(int indent) {
        this.indent = new Indent(indent);
    }

    @Override
    public void marshal(Element element, Destination destination) {
        requireObject(element);
        indent.reset();
        marshal(element, destination, indent, false, true);
        destination.flush();
    }

    private void marshal(Element element, Destination destination, Indent indent, boolean isValueOfKey, boolean isNotElementOfArray) {
        if (element == null) {
            if (isValueOfKey) {
                destination.append(' ');
            }
            destination.append("null");
        } else if (element instanceof String) {
            if (isValueOfKey) {
                destination.append(' ');
            }
            destination.append(((String) element).set());
        } else if (element instanceof Integer) {
            if (isValueOfKey) {
                destination.append(' ');
            }
            destination.append(((Integer) element).asLong());
        } else if (element instanceof Object) {
            Object object = (Object) element;
            if (object.isEmpty()) {
                if (isValueOfKey) {
                    destination.append(' ');
                }
                destination.append("{}");
            } else {
                indent.increment();
                boolean isFirst = true;
                for (KeyValue keyValue : object.getKeyValues()) {
                    if (isFirst) {
                        if (isNotElementOfArray) {
                            indent.appendTo(destination);
                        }
                        isFirst = false;
                    } else {
                        destination.append(ln);
                        indent.appendTo(destination);
                    }
                    destination.setCurrentTargetReplacements(stringEscapes);
                    destination.append(keyValue.getKey());
                    destination.removeCurrentTargetReplacements();
                    destination.append(':');
                    destination.checkFlush();
                    marshal(keyValue.getValue(), destination, indent, true, true);
                }
                indent.decrement();
            }
        } else if (element instanceof Array) {
            Array array = (Array) element;
            if (array.isEmpty()) {
                if (isValueOfKey) {
                    destination.append(' ');
                }
                destination.append("[]");
            } else {
                indent.increment();
                boolean isFirst = true;
                for (Element arrayElement : array) {
                    if (isFirst) {
                        if (indent.wasIncrementedAtLeastTwice()) {
                            destination.append(ln);
                        }
                        isFirst = false;
                    } else {
                        destination.append(ln);
                    }
                    indent.appendTo(destination);
                    destination.append("- ");
                    destination.checkFlush();
                    marshal(arrayElement, destination, indent, false, false);
                }
                indent.decrement();
            }
        } else if (element instanceof Decimal) {
            if (isValueOfKey) {
                destination.append(' ');
            }
            decimalConversion.appendDouble(((Decimal) element).asDouble(), destination);
        } else if (element instanceof Boolean) {
            if (isValueOfKey) {
                destination.append(' ');
            }
            destination.append(((Boolean) element).asString());
        } else {
            throw new AssertionError();
        }
    }

    @Override
    public Element unmarshal(Source source) {
        return null;
    }

    @Override
    public void gc() {
        indent.gc();

        keyBuilder = new StringBuilder();
    }
}
