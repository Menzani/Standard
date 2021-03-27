package eu.menzani.struct;

import eu.menzani.lang.Assume;
import eu.menzani.lang.Ensure;

public class JVMOption {
    public static final JVMOption UNLOCK_EXPERIMENTAL_VM_OPTIONS = xx("UnlockExperimentalVMOptions").setAsBoolean(true).view();
    public static final JVMOption UNLOCK_DIAGNOSTIC_VM_OPTIONS = xx("UnlockDiagnosticVMOptions").setAsBoolean(true).view();

    public static final JVMOption USE_LARGE_PAGES = xx("UseLargePages").setAsBoolean(true).view();
    public static final JVMOption ALWAYS_PRE_TOUCH = xx("AlwaysPreTouch").setAsBoolean(true).view();

    public static final JVMOption USE_EPSILON_GC = xx("UseEpsilonGC").setAsBoolean(true).view();

    public static final JVMOption ENABLE_CONTENDED_ANNOTATION = xx("RestrictContended").setAsBoolean(false).view();
    public static final JVMOption DISABLE_BIASED_LOCKING = xx("UseBiasedLocking").setAsBoolean(false).view();
    public static final JVMOption DISABLE_TIERED_COMPILATION = xx("TieredCompilation").setAsBoolean(false).view();
    public static final JVMOption DISABLE_PERF_DATA = xx("UsePerfData").setAsBoolean(false).view();

    public static final JVMOption PRINT_COMPILATION = xx("PrintCompilation").setAsBoolean(true).view();
    public static final JVMOption PRINT_INLINING = xx("PrintInlining").setAsBoolean(true).view();
    public static final JVMOption PRINT_ASSEMBLY = xx("PrintAssembly").setAsBoolean(true).view();

    public static final JVMOption BATCH = x("batch").view();

    private final Type type;
    private final String key;
    private Freeze freeze = Freeze.no();

    private Object value;

    public static JVMOption xms(MemorySize value) {
        return x("ms").setAsMemorySize(value);
    }

    public static JVMOption xmx(MemorySize value) {
        return x("mx").setAsMemorySize(value);
    }

    public static JVMOption x(String key) {
        return new JVMOption(Type.X, key);
    }

    public static JVMOption xx(String key) {
        return new JVMOption(Type.XX, key);
    }

    private JVMOption(Type type, String key) {
        this.type = type;
        this.key = key;
    }

    public Type getType() {
        return type;
    }

    public JVMOption setAsBoolean(boolean enabled) {
        freeze.check();
        value = enabled;
        return this;
    }

    public JVMOption setAsString(String value) {
        freeze.check();
        Assume.notNull(value);
        this.value = value;
        return this;
    }

    public JVMOption setAsLong(long value) {
        setAsString(Long.toString(value));
        return this;
    }

    public JVMOption setAsMemorySize(MemorySize value) {
        setAsLong(value.toBytes());
        return this;
    }

    public JVMOption view() {
        freeze = Freeze.yes();
        return this;
    }

    @Override
    public String toString() {
        assert type != null;
        switch (type) {
            case X: {
                String result = "-X" + key;
                if (value != null) {
                    result += value;
                }
                return result;
            }
            case XX:
                Ensure.notNull(value);
                String result = "-XX:";
                if (value == Boolean.TRUE) {
                    result += '+';
                } else if (value == Boolean.FALSE) {
                    result += '-';
                } else {
                    return result + key + '=' + value;
                }
                return result + key;
            default:
                throw new AssertionError();
        }
    }

    public enum Type {
        X,
        XX
    }
}
