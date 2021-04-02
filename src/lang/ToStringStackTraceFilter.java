package eu.menzani.lang;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ToStringStackTraceFilter extends StackTraceFilter {
    private final StringBuffer buffer;

    public ToStringStackTraceFilter() {
        this(new StringWriter(2048));
    }

    private ToStringStackTraceFilter(StringWriter writer) {
        super(new PrintWriter(writer));
        buffer = writer.getBuffer();
    }

    public String printFilteredStackTraceToString(Throwable throwable) {
        buffer.setLength(0);
        printFilteredStackTrace(throwable);
        return buffer.toString();
    }
}
