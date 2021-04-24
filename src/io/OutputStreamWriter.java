package eu.menzani.io;

import eu.menzani.struct.HeapBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class OutputStreamWriter extends Writer {
    private final OutputStream stream;
    private byte[] buffer = new byte[2];

    public OutputStreamWriter(OutputStream stream) {
        lock = null;
        this.stream = stream;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        int amount = len * 2;
        if (buffer.length < amount) {
            buffer = new byte[amount];
        }
        int j = 0;
        for (len += off; off < len; j += 2) {
            HeapBuffer.putChar(buffer, j, cbuf[off++]);
        }
        assert j == amount;
        stream.write(buffer, 0, amount);
    }

    @Override
    public void write(int c) throws IOException {
        HeapBuffer.putChar(buffer, 0, (char) c);
        stream.write(buffer, 0, 2);
    }

    @Override
    public void write(char[] cbuf) throws IOException {
        int length = cbuf.length;
        int amount = length * 2;
        if (buffer.length < amount) {
            buffer = new byte[amount];
        }
        int j = 0;
        for (int i = 0; i < length; j += 2) {
            HeapBuffer.putChar(buffer, j, cbuf[i++]);
        }
        assert j == amount;
        stream.write(buffer, 0, amount);
    }

    @Override
    public void write(String str) throws IOException {
        append(str);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        append(str, off, len);
    }

    @Override
    public Writer append(CharSequence csq) throws IOException {
        int length = csq.length();
        int amount = length * 2;
        if (buffer.length < amount) {
            buffer = new byte[amount];
        }
        int j = 0;
        for (int i = 0; i < length; j += 2) {
            HeapBuffer.putChar(buffer, j, csq.charAt(i++));
        }
        assert j == amount;
        stream.write(buffer, 0, amount);
        return this;
    }

    @Override
    public Writer append(CharSequence csq, int start, int end) throws IOException {
        int amount = (end - start) * 2;
        if (buffer.length < amount) {
            buffer = new byte[amount];
        }
        int j = 0;
        for (; start < end; j += 2) {
            HeapBuffer.putChar(buffer, j, csq.charAt(start++));
        }
        assert j == amount;
        stream.write(buffer, 0, amount);
        return this;
    }

    @Override
    public Writer append(char c) throws IOException {
        HeapBuffer.putChar(buffer, 0, c);
        stream.write(buffer, 0, 2);
        return this;
    }

    @Override
    public void flush() throws IOException {
        stream.flush();
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
