package eu.menzani.test;

import java.lang.invoke.MethodHandle;

class TestClass extends TestElement {
    private final Class<?> clazz;
    private final String name;
    private final MethodHandle constructor;
    private final TestMethod[] testMethods;

    TestClass(Class<?> clazz, String name, MethodHandle constructor, TestMethod[] testMethods) {
        super(clazz);
        this.clazz = clazz;
        this.name = name;
        this.constructor = constructor;
        this.testMethods = testMethods;
    }

    MethodHandle getConstructor() {
        return constructor;
    }

    TestMethod[] getTestMethods() {
        return testMethods;
    }

    void initTestMethods() {
        for (TestMethod testMethod : testMethods) {
            testMethod.initTestClass(this);
        }
    }

    void validate() {
        if (!clazz.desiredAssertionStatus()) {
            System.err.println(this);
            System.err.println("Assertions are disabled");
        }
    }

    boolean shouldExecuteAll() {
        for (TestMethod testMethod : testMethods) {
            if (testMethod.shouldExecuteOnlyThis()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
