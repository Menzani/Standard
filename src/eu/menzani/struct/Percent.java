package eu.menzani.struct;

import eu.menzani.lang.Assume;
import eu.menzani.lang.Cast;

public class Percent {
    public static final Percent ZERO = new Percent(0);
    public static final Percent TWENTY_FIVE = new Percent(25);
    public static final Percent FIFTY = new Percent(50);
    public static final Percent SEVENTY_FIVE = new Percent(75);
    public static final Percent ONE_HUNDRED = new Percent(100);

    private final double value;

    public Percent(int value) {
        Assume.between(value, 0, 100);
        this.value = value;
    }

    public Percent(float value) {
        Assume.between(value, 0F, 100F);
        this.value = value;
    }

    public Percent(double value) {
        Assume.between(value, 0D, 100D);
        this.value = value;
    }

    public int getAsInt() {
        return Cast.withoutOverflow(getAsLong());
    }

    public long getAsLong() {
        return Math.round(value);
    }

    public float getAsFloat() {
        return Cast.withoutOverflow(value);
    }

    public double get() {
        return value;
    }

    public float getAsFractionAsFloat() {
        return Cast.withoutOverflow(getAsFraction());
    }

    public double getAsFraction() {
        return value / 100D;
    }

    @Override
    public String toString() {
        return Cast.toString(value) + '%';
    }
}
