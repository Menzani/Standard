package eu.menzani.lang;

public class NumbersTest {
    public void ceilDiv() {
        Assert.equal(Numbers.ceilDiv(0, 5), 1);
        Assert.equal(Numbers.ceilDiv(1, 5), 1);
        Assert.equal(Numbers.ceilDiv(2, 5), 1);
        Assert.equal(Numbers.ceilDiv(3, 5), 1);
        Assert.equal(Numbers.ceilDiv(4, 5), 1);
        Assert.equal(Numbers.ceilDiv(6, 5), 2);
        Assert.equal(Numbers.ceilDiv(1_000_000, 3), 333_333 + 1);

        Assert.fails(() -> Numbers.ceilDiv(5, 0), ArithmeticException.class);
        Assert.equal(Numbers.ceilDiv(5, 1), 5);
        Assert.equal(Numbers.ceilDiv(5, 2), 3);
        Assert.equal(Numbers.ceilDiv(5, 3), 2);
        Assert.equal(Numbers.ceilDiv(5, 4), 2);
        Assert.equal(Numbers.ceilDiv(5, 5), 1);
        Assert.equal(Numbers.ceilDiv(5, 6), 1);
        Assert.equal(Numbers.ceilDiv(5, 50), 1);
    }

    public void getNextPowerOfTwo() {
        Assert.equal(Numbers.getNextPowerOfTwo(128), 128);
        Assert.equal(Numbers.getNextPowerOfTwo(129), 256);

        Assert.equal(Numbers.getNextPowerOfTwo(-128), 0);
        Assert.equal(Numbers.getNextPowerOfTwo(-129), 0);
    }

    public void isPowerOfTwo() {
        assert Numbers.isPowerOfTwo(128);
        assert !Numbers.isPowerOfTwo(127);
        assert Numbers.isPowerOfTwo(0);
        assert Numbers.isPowerOfTwo(2);

        assert !Numbers.isPowerOfTwo(-128);
        assert !Numbers.isPowerOfTwo(-127);
        assert !Numbers.isPowerOfTwo(-2);
    }
}
