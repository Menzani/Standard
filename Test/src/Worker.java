package eu.menzani.test;

import java.lang.reflect.InvocationTargetException;

class Worker extends Thread {
    private final WorkerManager manager;

    Worker(WorkerManager manager) {
        super("tester");
        this.manager = manager;
    }

    @Override
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

                if (e instanceof ExceptionInInitializerError) {
                    e = e.getCause();
                }
                synchronized (System.err) {
                    System.err.println(testClass);
                    e.printStackTrace();
                }
                manager.addFailedTest(testClass);
                continue;
            }

            try {
                testMethod.getReflected().invoke(instance);
            } catch (InvocationTargetException e) {
                synchronized (System.err) {
                    System.err.println(testMethod);
                    e.getCause().printStackTrace();
                }
                manager.addFailedTest(testMethod);
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }
    }
}
