package eu.menzani.struct;

import eu.menzani.lang.Assert;

public class MemorySizeTest {
    public void getUnit() {
        Assert.same(MemorySize.ofBytes(1025).getUnit(), MemorySize.Unit.KILOBYTE);
        Assert.same(MemorySize.ofGigabytes(0.15).getUnit(), MemorySize.Unit.MEGABYTE);
    }

    public void getNextPowerOfTwo() {
        MemorySize fourKilobytes = MemorySize.ofKilobytes(4);
        MemorySize eightPetabytes = MemorySize.ofPetabytes(8);

        Assert.same(fourKilobytes.getNextPowerOfTwo(), fourKilobytes);
        Assert.equal(MemorySize.ofPetabytes(4.9).getNextPowerOfTwo(), eightPetabytes);
        Assert.equal(MemorySize.ofPetabytes(5.1).getNextPowerOfTwo(), eightPetabytes);
    }
}
