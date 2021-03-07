package eu.menzani.concurrent;

import eu.menzani.lang.Nonblocking;

import java.util.function.Supplier;

public class ThreadGroup {
    private final Thread[] threads;

    public ThreadGroup(int size) {
        threads = new Thread[size];
    }

    public void create(Supplier<? extends Thread> factory) {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = factory.get();
        }
    }

    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public void join() {
        for (Thread thread : threads) {
            Nonblocking.join(thread);
        }
    }

    public void run(Supplier<? extends Thread> factory) {
        create(factory);
        start();
        join();
    }
}
