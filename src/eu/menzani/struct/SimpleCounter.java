package eu.menzani.struct;

public class SimpleCounter implements Counter {
    private int value;

    {
        reset();
    }

    @Override
    public int increment() {
        return value++;
    }

    @Override
    public void reset() {
        value = 1;
    }
}
