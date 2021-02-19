package eu.menzani.struct;

import eu.menzani.lang.Immutable;

@Immutable
public final class Freeze {
    private static final Freeze yes = new Freeze(true);
    private static final Freeze no = new Freeze(false);

    public static Freeze yes() {
        return yes;
    }

    public static Freeze no() {
        return no;
    }

    private final boolean frozen;

    private Freeze(boolean frozen) {
        this.frozen = frozen;
    }

    public void check() {
        if (frozen) {
            throw new UnsupportedOperationException();
        }
    }
}
