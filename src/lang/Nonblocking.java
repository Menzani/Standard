package eu.menzani.lang;

import java.util.concurrent.BlockingQueue;

public class Nonblocking {
    public static <E> E take(BlockingQueue<E> queue) {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }

    public static <E> void put(BlockingQueue<E> queue, E element) {
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }

    public static void sleep(long milliseconds) {
        try {
            java.lang.Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }

    public static void join(java.lang.Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }

    public static void waitFor(Process process) {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }
}
