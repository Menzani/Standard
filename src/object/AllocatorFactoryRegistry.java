package eu.menzani.object;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

class AllocatorFactoryRegistry {
    static final SortedSet<AllocatorFactory> FACTORIES = new ConcurrentSkipListSet<>();
}
