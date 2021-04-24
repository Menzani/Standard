package eu.menzani.io;

import eu.menzani.lang.Assert;
import eu.menzani.lang.Cast;
import eu.menzani.struct.HeapBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.CharBuffer;

public class InputStreamReader extends Reader {
    private final InputStream stream;
    private byte[] buffer = new byte[2];
    private byte[] skipBuffer = HeapBuffer.EMPTY;

    public InputStreamReader(InputStream stream) {
        lock = null;
        this.stream = stream;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int amount = len * 2;
        if (buffer.length < amount) {
            buffer = new byte[amount];
        }
        int amountRead = stream.read(buffer, 0, amount);
        if (amountRead == -1) {
            return -1;
        }
        int j = 0;
        for (len += off; off < len; j += 2) {
            cbuf[off++] = HeapBuffer.getChar(buffer, j);
        }
        assert j == amountRead;
        return amountRead;
    }

    @Override
    public int read(CharBuffer target) throws IOException {
        int len = target.remaining();
        int amount = len * 2;
        if (buffer.length < amount) {
            buffer = new byte[amount];
        }
        int amountRead = stream.read(buffer, 0, amount);
        if (amountRead == -1) {
            return -1;
        }
        int j = 0;
        int position = target.position();
        for (len += position; position++ < len; j += 2) {
            target.put(HeapBuffer.getChar(buffer, j));
        }
        assert j == amountRead;
        return amountRead;
    }

    @Override
    public int read() throws IOException {
        int amountRead = stream.read(buffer, 0, 2);
        if (amountRead == -1) {
            return -1;
        }
        Assert.equal(amountRead, 2);
        return HeapBuffer.getChar(buffer, 0);
    }

    @Override
    public int read(char[] cbuf) throws IOException {
        int length = cbuf.length;
        int amount = length * 2;
        if (buffer.length < amount) {
            buffer = new byte[amount];
        }
        int amountRead = stream.read(buffer, 0, amount);
        if (amountRead == -1) {
            return -1;
        }
        int j = 0;
        for (int i = 0; i < length; j += 2) {
            cbuf[i++] = HeapBuffer.getChar(buffer, j);
        }
        assert j == amountRead;
        return amountRead;
    }

    @Override
    public long skip(long n) throws IOException {
        int nInt = Cast.withoutOverflow(n);
        if (skipBuffer.length < nInt) {
            skipBuffer = new byte[nInt];
        }
        return stream.read(skipBuffer, 0, Math.multiplyExact(nInt, 2));
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
