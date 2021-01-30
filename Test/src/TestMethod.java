package eu.menzani.test;

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

    Method getReflected() {
        return method;
    }

    void initTestClass(TestClass testClass) {
        this.testClass = testClass;
    }

    @Override
    public int hashCode() {
        // Taken from Method#hashCode()
        return testClass.toString().hashCode() ^ method.getName().hashCode();
    }

    @Override
    public String toString() {
        // + operator is not optimized
        StringBuilder builder = new StringBuilder(128);
        builder.append(testClass.toString());
        builder.append(' ');
        builder.append(method.getName());
        return builder.toString();
    }
}
