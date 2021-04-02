package eu.menzani.error;

import eu.menzani.lang.ToStringStackTraceFilter;

public class GlobalStackTraceFilter {
    private static final ToStringStackTraceFilter instance = new ToStringStackTraceFilter();

    public static ToStringStackTraceFilter getInstance() {
        return instance;
    }
}
