package eu.menzani.test;

import eu.menzani.lang.Invokable;

import java.lang.reflect.Method;

class TestMethod extends TestElement {
    private final Method method;
    private TestClass testClass;

    TestMethod(Method method) {
        super(method);
        this.method = method;
    }

    TestClass getTestClass() {
        return testClass;
    }

    void runTest(Object instance) {
        eu.menzani.lang.Method<?> invokable = Invokable.of(method);
        invokable.setTargetInstance(instance);
        invokable.call();
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
