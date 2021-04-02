package eu.menzani.test;

import eu.menzani.lang.RemovedFromStackTrace;

class Worker extends Thread {
    private final WorkerManager manager;

    Worker(WorkerManager manager) {
        this.manager = manager;
    }

    @Override
    @RemovedFromStackTrace("eu.menzani.test.WorkerManager.stackTraceFilter")
    public void run() {
        TestMethod testMethod;
        while ((testMethod = manager.next()) != null) {
            TestClass testClass = testMethod.getTestClass();

            if (manager.isCancelled(testClass)) {
                continue;
            }

            Object instance;
            try {
                instance = testClass.getConstructor().invoke();
                manager.willNotCancel();
            } catch (Throwable e) {
                manager.cancel(testClass);
                manager.onFailure(e, testClass);
                continue;
            }

            if (manager.shouldPrintCurrentTestMethod()) {
                System.out.println(testMethod);
            }
            try {
                testMethod.runTest(instance);
            } catch (Throwable e) {
                manager.onFailure(e, testMethod);
            }
        }
    }
}
