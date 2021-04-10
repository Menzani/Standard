package eu.menzani.lang;

import eu.menzani.collection.ResizableArray;
import eu.menzani.data.ParseException;

public class CharSequenceSplitter {
    private static final String[] defaultNoMatchValue = new String[0];
    private static final char escapeChar = '\\';

    private final char delimiter;
    private final String[] noMatchValue;

    public CharSequenceSplitter(char delimiter) {
        this(delimiter, defaultNoMatchValue);
    }

    public CharSequenceSplitter(char delimiter, String[] noMatchValue) {
        this.delimiter = delimiter;
        this.noMatchValue = noMatchValue;
    }

    public String[] split(CharSequence charSequence) {
        ResizableArray<String> result = new ResizableArray<>(String.class);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < charSequence.length(); ) {
            char currentChar = charSequence.charAt(i++);
            if (currentChar == delimiter) {
                if (builder.length() != 0) {
                    result.add(builder.toString());
                    builder.setLength(0);
                }
            } else if (currentChar == escapeChar) {
                char currentChar2 = charSequence.charAt(i++);
                if (currentChar2 == delimiter) {
                    builder.append(delimiter);
                } else if (currentChar2 == escapeChar) {
                    builder.append(escapeChar);
                } else {
                    throw new ParseException();
                }
            } else {
                builder.append(currentChar);
            }
        }
        if (builder.length() != 0) {
            result.add(builder.toString());
        }
        return result.asFixedArrayOr(noMatchValue);
    }
}
