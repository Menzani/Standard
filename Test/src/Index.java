package eu.menzani.test;

import eu.menzani.misc.Arrays;

class Index {
    private final TestClass[] testClasses;
    private final boolean executeAll;

    Index(TestClass[] testClasses) {
        this.testClasses = testClasses;
        executeAll = computeExecuteAll();
    }

    private boolean computeExecuteAll() {
        for (TestClass testClass : testClasses) {
            if (testClass.shouldExecuteOnlyThis() || testClass.shouldNotExecuteAll()) {
                return false;
            }
        }
        return true;
    }

    TestClass[] getTestClasses() {
        return testClasses;
    }

    void validate() {
        for (TestClass testClass : testClasses) {
            testClass.validate();
        }
    }

    boolean shouldExecuteAll() {
        return executeAll;
    }

    @Override
    public int hashCode() {
        return Arrays.orderIndependentHashCode(testClasses);
    }
}
