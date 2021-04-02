package eu.menzani.lang;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class StackTraceFilter {
    private final List<String> methodsToRemove = new CopyOnWriteArrayList<>();
    private final Object output;

    public StackTraceFilter() {
        this(System.err);
    }

    public StackTraceFilter(PrintStream output) {
        this.output = new FilteredPrintStream(output);
    }

    public StackTraceFilter(PrintWriter output) {
        this.output = new FilteredPrintWriter(output);
    }

    public StackTraceFilter addMethodsToRemove(StackTraceFilter copyFrom) {
        methodsToRemove.addAll(copyFrom.methodsToRemove);
        return this;
    }

    public StackTraceFilter addMethodToRemove(Class<?> clazz, String methodName) {
        addMethodToRemove(Classes.getFullName(clazz), methodName);
        return this;
    }

    public StackTraceFilter addMethodToRemove(String classFullName, String methodName) {
        methodsToRemove.add(classFullName + '.' + methodName + '(');
        return this;
    }

    public void printFilteredStackTrace(Throwable throwable) {
        if (output instanceof PrintStream) {
            throwable.printStackTrace((PrintStream) output);
        } else {
            throwable.printStackTrace((PrintWriter) output);
        }
    }

    private boolean shouldDelegate(Object x) {
        if (x instanceof Throwable) {
            return true;
        }
        String line = (String) x;
        if (line.startsWith("\tat ")) {
            return shouldNotBeRemoved(line);
        }
        return true;
    }

    private boolean shouldNotBeRemoved(String line) {
        for (String methodToRemove : methodsToRemove) {
            if (line.startsWith(methodToRemove, 4)) {
                return false;
            }
        }
        return true;
    }

    private class FilteredPrintWriter extends PrintWriter {
        FilteredPrintWriter(Writer delegate) {
            super(delegate);
        }

        @Override
        public void println(Object x) {
            if (shouldDelegate(x)) {
                super.println(x);
            }
        }
    }

    private class FilteredPrintStream extends PrintStream {
        FilteredPrintStream(PrintStream delegate) {
            super(delegate);
        }

        @Override
        public void println(Object x) {
            if (shouldDelegate(x)) {
                super.println(x);
            }
        }
    }
}
