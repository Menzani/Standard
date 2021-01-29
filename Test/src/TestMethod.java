package eu.menzani.test;

import java.lang.reflect.Method;

class TestMethod extends TestElement {
    private final Method method;
    private TestClass testClass;

    TestMethod(Method method) {
        super(method);
        this.method = method;
    }

    Method getReflected() {
        return method;
    }

    void initTestClass(TestClass testClass) {
        this.testClass = testClass;
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

    @Override
    public String toString() {
        return testClass.toString() + ' ' + method.getName();
    }
}
