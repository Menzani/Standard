package eu.menzani.io;

import eu.menzani.collection.IterableIterator;
import eu.menzani.lang.UncaughtException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InputStreamIterator implements IterableIterator<String> {
    private final BufferedReader reader;
    private String line;

    public InputStreamIterator(InputStream stream, Charset charset) {
        reader = new BufferedReader(new InputStreamReader(stream, charset));
    }

    @Override
    public boolean hasNext() {
        try {
            line = reader.readLine();
            if (line == null) {
                reader.close();
                return false;
            }
            return true;
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    @Override
    public String next() {
        return line;
    }
}
