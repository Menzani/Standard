package eu.menzani.data.marshaller;

import eu.menzani.data.Boolean;
import eu.menzani.data.Integer;
import eu.menzani.data.Object;
import eu.menzani.data.String;
import eu.menzani.data.*;
import eu.menzani.lang.DecimalConversion;
import eu.menzani.lang.StringBuilders;
import eu.menzani.lang.TargetReplacement;
import eu.menzani.object.GarbageCollectionAware;

public class CompactJsonMarshaller extends Marshaller implements GarbageCollectionAware {
    private static final TargetReplacement[] stringEscapes = {new TargetReplacement('\\', "\\\\"), new TargetReplacement('\"', "\\\"")};

    private final DecimalConversion decimalConversion = new DecimalConversion();
    private StringBuilder keyBuilder;

    public CompactJsonMarshaller() {
        gc();
    }

    @Override
    public void marshal(Element element, WriteBuffer buffer) {
        marshal(element, buffer, buffer.getBuilder());
        buffer.flush();
    }

    private void marshal(Element element, WriteBuffer buffer, StringBuilder builder) {
        if (element == null) {
            builder.append((java.lang.String) null);
        } else if (element instanceof String) {
            builder.append('"');
            int start = buffer.position();
            builder.append(((String) element).asCharSequence());
            StringBuilders.replace(builder, start, buffer.position(), stringEscapes);
            builder.append('"');
        } else if (element instanceof Integer) {
            builder.append(((Integer) element).asLong());
        } else if (element instanceof Object) {
            builder.append('{');
            Object object = (Object) element;
            if (object.isNotEmpty()) {
                for (KeyValue keyValue : object.getKeyValues()) {
                    builder.append('"');
                    int start = buffer.position();
                    builder.append(keyValue.getKey());
                    StringBuilders.replace(builder, start, buffer.position(), stringEscapes);
                    builder.append("\":");
                    buffer.checkFull();
                    marshal(keyValue.getValue(), buffer);
                    builder.append(',');
                }
                builder.setLength(builder.length() - 1);
            }
            builder.append('}');
        } else if (element instanceof Array) {
            builder.append('[');
            Array array = (Array) element;
            if (array.isNotEmpty()) {
                for (Element arrayElement : array) {
                    buffer.checkFull();
                    marshal(arrayElement, buffer);
                    builder.append(',');
                }
                builder.setLength(builder.length() - 1);
            }
            builder.append(']');
        } else if (element instanceof Decimal) {
            decimalConversion.appendDouble(((Decimal) element).asDouble(), builder);
        } else if (element instanceof Boolean) {
            builder.append(((Boolean) element).asPrimitive());
        } else {
            throw new AssertionError();
        }
    }

    @Override
    public Element unmarshal(Source source) {
        switch (source.next()) {
            case 'n':
                if (source.nextIs('u') &&
                        source.nextIs('l') &&
                        source.nextIs('l')) {
                    return null;
                }
                throw new ParseException();
            case 't':
                if (source.nextIs('r') &&
                        source.nextIs('u') &&
                        source.nextIs('e')) {
                    return Boolean.TRUE;
                }
                throw new ParseException();
            case 'f':
                if (source.nextIs('a') &&
                        source.nextIs('l') &&
                        source.nextIs('s') &&
                        source.nextIs('e')) {
                    return Boolean.FALSE;
                }
                throw new ParseException();
            case '"': {
                String string = String.allocate();
                StringBuilder builder = string.set();
                outer:
                while (true) {
                    char character = source.next();
                    switch (character) {
                        case '\\':
                            switch (source.next()) {
                                case '"':
                                    builder.append('"');
                                    break;
                                case '\\':
                                    builder.append('\\');
                                    break;
                                default:
                                    throw new ParseException();
                            }
                            break;
                        case '"':
                            break outer;
                        default:
                            builder.append(character);
                    }
                }
                return string;
            }
            case '[': {
                Array array = Array.allocate();
                while (true) {
                    if (source.peekIs(']')) {
                        source.advance();
                        return array;
                    }
                    array.add(unmarshal(source));
                    if (source.peekIs(',')) {
                        source.advance();
                    }
                }
            }
            case '{':
                Object object = Object.allocate();
                while (true) {
                    if (source.peekIs('}')) {
                        source.advance();
                        return object;
                    }

                    if (source.nextIsNot('"')) {
                        throw new ParseException();
                    }
                    StringBuilder builder = keyBuilder;
                    builder.setLength(0);
                    outer:
                    while (true) {
                        char character = source.next();
                        switch (character) {
                            case '\\':
                                switch (source.next()) {
                                    case '"':
                                        builder.append('"');
                                        break;
                                    case '\\':
                                        builder.append('\\');
                                        break;
                                    default:
                                        throw new ParseException();
                                }
                                break;
                            case '"':
                                break outer;
                            default:
                                builder.append(character);
                        }
                    }

                    if (source.nextIsNot(':')) {
                        throw new ParseException();
                    }
                    object.set(builder.toString(), unmarshal(source));
                    if (source.peekIs(',')) {
                        source.advance();
                    }
                }
            case '+':
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case 'I':
            case 'N':
                int start = source.position() - 1;
                boolean isDecimal = false;
                loop:
                while (source.hasNext()) {
                    switch (source.peek()) {
                        case ',':
                        case ']':
                        case '}':
                            break loop;
                        case '.':
                            isDecimal = true;
                        default:
                            source.advance();
                    }
                }

                if (isDecimal) {
                    return Decimal.allocate(decimalConversion.parseDouble(source, start, source.position()));
                }
                return Integer.allocate(Long.parseLong(source, start, source.position(), 10));
            default:
                throw new ParseException();
        }
    }

    @Override
    public void gc() {
        keyBuilder = new StringBuilder();
    }
}
