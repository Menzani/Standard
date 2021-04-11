package eu.menzani.lang;

import eu.menzani.error.GlobalStackTraceFilter;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class StackTraceFilter {
    private final Set<String> methodsToRemove = new CopyOnWriteArraySet<>();
    private final Set<StackTraceFilter> inheritFrom = new CopyOnWriteArraySet<>();

    private final Object output;
    private boolean isFirstLine;

    public StackTraceFilter() {
        this(System.err);
    }

    public StackTraceFilter(PrintStream output) {
        this.output = new FilteredPrintStream(output);
    }

    public StackTraceFilter(PrintWriter output) {
        this.output = new FilteredPrintWriter(output);
    }

    public StackTraceFilter inheritFromGlobal() {
        inheritFrom(GlobalStackTraceFilter.getInstance());
        return this;
    }

    public StackTraceFilter inheritFrom(StackTraceFilter filter) {
        inheritFrom.add(filter);
        return this;
    }

    public StackTraceFilter stopInheritingFrom(StackTraceFilter filter) {
        inheritFrom.remove(filter);
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
        isFirstLine = true;
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
            if (isFirstLine) {
                isFirstLine = false;
                return true;
            }
            return shouldNotBeRemoved(line);
        }
        return true;
    }

    private boolean shouldNotBeRemoved(String line) {
        if (shouldBeRemoved(this, line)) {
            return false;
        }
        for (StackTraceFilter filter : inheritFrom) {
            if (shouldBeRemoved(filter, line)) {
                return false;
            }
        }
        return true;
    }

    private static boolean shouldBeRemoved(StackTraceFilter filter, String line) {
        for (String methodToRemove : filter.methodsToRemove) {
            if (line.startsWith(methodToRemove, 4)) {
                return true;
            }
        }
        return false;
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
