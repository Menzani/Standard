package eu.menzani.test;

import eu.menzani.error.GlobalExceptionHandler;
import eu.menzani.lang.Nonblocking;
import eu.menzani.lang.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class WorkerManager {
    private final FailedTests failedTests;

    private final List<TestMethod> testMethodsBuilder = new ArrayList<>();
    private Queue<TestMethod> testMethods;

    private final List<TestClass> cancelled = new ArrayList<>();
    private final Lock cancelLock = new ReentrantLock();

    private boolean printCurrentTestMethod;

    WorkerManager(FailedTests failedTests) {
        this.failedTests = failedTests;
    }

    void enqueue(TestMethod testMethod) {
        testMethodsBuilder.add(testMethod);
    }

    void setPrintCurrentTestMethod(boolean printCurrentTestMethod) {
        this.printCurrentTestMethod = printCurrentTestMethod;
    }

    void run(int parallelism) {
        testMethods = new ConcurrentLinkedQueue<>(testMethodsBuilder);

        Thread[] workers = new Thread[parallelism];
        for (int i = 0; i < parallelism; i++) {
            Thread worker = new Worker(this);
            worker.start();
            workers[i] = worker;
        }
        for (Thread worker : workers) {
            Nonblocking.join(worker);
        }
    }

    @Optional TestMethod next() {
        return testMethods.poll();
    }

    boolean isCancelled(TestClass testClass) {
        cancelLock.lock();
        return cancelled.contains(testClass);
    }

    void willNotCancel() {
        cancelLock.unlock();
    }

    void cancel(TestClass testClass) {
        cancelled.add(testClass);
        cancelLock.unlock();
    }

    void onFailure(Throwable throwable, TestElement testElement) {
        throwable = GlobalExceptionHandler.process(throwable);
        if (throwable == null) return;

        synchronized (System.err) {
            System.err.println(testElement);
            throwable.printStackTrace();
        }

        failedTests.add(testElement);
    }

    boolean shouldPrintCurrentTestMethod() {
        return printCurrentTestMethod;
    }
}
