package eu.menzani.data;

import eu.menzani.lang.TargetReplacement;

import java.util.Arrays;

public class CharArrayDestination extends Destination {
    private char[] array;
    private int position;

    public CharArrayDestination() {
        this(512);
    }

    public CharArrayDestination(int initialLength) {
        array = new char[initialLength];
    }

    @Override
    public void append(char character) {
        array[position++] = character;
    }

    @Override
    public void append(java.lang.String string) {
        int position = this.position;
        int length = string.length();
        int newPosition = position + length;
        if (newPosition >= array.length) {
            array = Arrays.copyOf(array, newPosition * 2);
        }
        string.getChars(0, length, array, position);
        if (shouldReplace()) {
            newPosition = replace(position, newPosition);
        }
        this.position = newPosition;
    }

    private int replace(int position, int newPosition) {
        while (position < newPosition) {
            char character = array[position];
            boolean noneMatched = true;
            for (TargetReplacement targetReplacement : getTargetReplacements()) {
                if (character == targetReplacement.getTarget()) {
                    CharSequence replacement = targetReplacement.getReplacement();
                    array[position] = replacement.charAt(0);
                    int replacementLength = replacement.length();
                    int replacementLengthMinusOne = replacementLength - 1;
                    newPosition += replacementLengthMinusOne;
                    if (newPosition >= array.length) {
                        array = Arrays.copyOf(array, newPosition * 2);
                    }
                    System.arraycopy(array, position + 1, array, position + replacementLength, replacementLengthMinusOne);
                    for (int i = 1; i < replacementLength; i++) {
                        array[position + i] = replacement.charAt(i);
                    }
                    position += replacementLength;
                    noneMatched = false;
                    break;
                }
            }
            if (noneMatched) {
                position++;
            }
        }
        return newPosition;
    }

    @Override
    public void append(StringBuilder builder) {
        int position = this.position;
        int length = builder.length();
        int newPosition = position + length;
        if (newPosition > array.length) {
            array = Arrays.copyOf(array, newPosition * 2);
        }
        builder.getChars(0, length, array, position);
        if (shouldReplace()) {
            newPosition = replace(position, newPosition);
        }
        this.position = newPosition;
    }

    @Override
    public void deleteLastChar() {
        position--;
    }

    public char[] getArray() {
        return Arrays.copyOf(array, position);
    }
}
