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

    @Override
    public ToStringStackTraceFilter inheritFromGlobal() {
        super.inheritFromGlobal();
        return this;
    }

    @Override
    public ToStringStackTraceFilter inheritFrom(StackTraceFilter filter) {
        super.inheritFrom(filter);
        return this;
    }

    @Override
    public ToStringStackTraceFilter stopInheritingFrom(StackTraceFilter filter) {
        super.stopInheritingFrom(filter);
        return this;
    }

    @Override
    public ToStringStackTraceFilter addMethodToRemove(Class<?> clazz, String methodName) {
        super.addMethodToRemove(clazz, methodName);
        return this;
    }

    @Override
    public ToStringStackTraceFilter addMethodToRemove(String classFullName, String methodName) {
        super.addMethodToRemove(classFullName, methodName);
        return this;
    }

    public String printFilteredStackTraceToString(Throwable throwable) {
        buffer.setLength(0);
        printFilteredStackTrace(throwable);
        return buffer.toString();
    }
}
