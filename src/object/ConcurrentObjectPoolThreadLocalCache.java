package eu.menzani.object;

import eu.menzani.concurrent.CleanedThreadLocal;

public class ConcurrentObjectPoolThreadLocalCache<T extends PoolObject> implements ObjectPool<T> {
    private final ObjectPool<T> parent;
    private final Cache<T> cache;

    public ConcurrentObjectPoolThreadLocalCache(ObjectPool<T> parent) {
        this.parent = parent;
        cache = new Cache<>(parent);
    }

    @Override
    public T release() {
        T cached = cache.get();
        if (cached == null) {
            return parent.release();
        }
        cache.set(null);
        return cached;
    }

    @Override
    public void acquire(T object) {
        if (cache.get() == null) {
            cache.set(object);
        } else {
            parent.acquire(object);
        }
    }

    @Override
    public void gc() {
        parent.gc();
    }

    private static class Cache<T extends PoolObject> extends CleanedThreadLocal<T> {
        private final ObjectPool<T> parent;

        Cache(ObjectPool<T> parent) {
            this.parent = parent;
        }

        @Override
        protected T newInitialValue() {
            return null;
        }

        @Override
        protected void onCleaned(T value) {
            parent.acquire(value);
        }
    }
}
