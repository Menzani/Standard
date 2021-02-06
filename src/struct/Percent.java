package eu.menzani.struct;

import eu.menzani.lang.Assume;
import eu.menzani.lang.Cast;

public class Percent {
    public static final Percent ONE_THIRD = new Percent(1D / 3D);
    public static final Percent TWO_THIRDS = new Percent(2D / 3D);

    public static final Percent ZERO = new Percent(0);
    public static final Percent TWENTY_FIVE = new Percent(25);
    public static final Percent FIFTY = new Percent(50);
    public static final Percent SEVENTY_FIVE = new Percent(75);
    public static final Percent ONE_HUNDRED = new Percent(100);

    public static final Percent TEN = new Percent(10);
    public static final Percent TWENTY = new Percent(20);
    public static final Percent THIRTY = new Percent(30);
    public static final Percent FOURTY = new Percent(40);
    public static final Percent SIXTY = new Percent(60);
    public static final Percent SEVENTY = new Percent(70);
    public static final Percent EIGHTY = new Percent(80);
    public static final Percent NINETY = new Percent(90);

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
