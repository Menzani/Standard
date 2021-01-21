package eu.menzani.lang;

import java.util.Random;

public class IntPairTest {
    private static final Random random = new Random();

    public void packIntegers() {
        int first = random.nextInt();
        int second = random.nextInt();
        long intPair = IntPair.of(first, second);
        Assert.equalTo(IntPair.getFirst(intPair), first);
        Assert.equalTo(IntPair.getSecond(intPair), second);
    }

    public void splitLong() {
        long intPair = random.nextLong();
        int first = IntPair.getFirst(intPair);
        int second = IntPair.getSecond(intPair);
        Assert.equalTo(IntPair.of(first, second), intPair);
    }
}
