package eu.menzani.system;

import eu.menzani.lang.Assert;

public class ThreadSpreaderTest {
    public void nonCyclying() {
        ThreadSpreader spreader = Threads.spreadOverCPUs().fromFirstCPU().toCPU(5).skipHyperthreads().build();
        Assert.equal(spreader.nextCPU(), 0);
        Assert.equal(spreader.nextCPU(), 2);
        Assert.equal(spreader.nextCPU(), 4);
        Assert.fails(spreader::nextCPU, ThreadManipulationException.class);
        Assert.fails(spreader::nextCPU, ThreadManipulationException.class);
        spreader.reset();
        Assert.equal(spreader.nextCPU(), 0);
        Assert.equal(spreader.nextCPU(), 2);
        Assert.equal(spreader.nextCPU(), 4);
        Assert.fails(spreader::nextCPU, ThreadManipulationException.class);
        Assert.fails(spreader::nextCPU, ThreadManipulationException.class);
    }

    public void cyclying() {
        ThreadSpreader spreader = Threads.spreadOverCPUs().fromFirstCPU().toCPU(5).skipHyperthreads().cycle().build();
        Assert.equal(spreader.nextCPU(), 0);
        Assert.equal(spreader.nextCPU(), 2);
        Assert.equal(spreader.nextCPU(), 4);
        Assert.equal(spreader.nextCPU(), 0);
        Assert.equal(spreader.nextCPU(), 2);
        spreader.reset();
        Assert.equal(spreader.nextCPU(), 0);
        Assert.equal(spreader.nextCPU(), 2);
        Assert.equal(spreader.nextCPU(), 4);
        Assert.equal(spreader.nextCPU(), 0);
        Assert.equal(spreader.nextCPU(), 2);
    }
}
