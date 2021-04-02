package eu.menzani.test;

import eu.menzani.concurrent.ThreadGroup;
import eu.menzani.error.GlobalExceptionHandler;
import eu.menzani.error.GlobalStackTraceFilter;
import eu.menzani.lang.Optional;
import eu.menzani.lang.StackTraceFilter;

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

    private boolean atLeastOneFailureOccurred;
    private boolean printCurrentTestMethod;

    private final StackTraceFilter stackTraceFilter = new StackTraceFilter()
            .addMethodsToRemove(GlobalStackTraceFilter.getInstance())
            .addMethodToRemove(TestMethod.class, "runTest")
            .addMethodToRemove(Worker.class, "run");

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

        ThreadGroup workers = new ThreadGroup(parallelism);
        workers.run(() -> new Worker(this));
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
            if (atLeastOneFailureOccurred) {
                System.err.println();
            }
            atLeastOneFailureOccurred = true;

            if (!printCurrentTestMethod || !(testElement instanceof TestMethod)) {
                System.err.println(testElement);
            }

            stackTraceFilter.printFilteredStackTrace(throwable);
        }

        failedTests.add(testElement);
    }

    boolean shouldPrintCurrentTestMethod() {
        return printCurrentTestMethod;
    }
}
