package eu.menzani.test;

class Index {
    private final TestClass[] testClasses;

    Index(TestClass[] testClasses) {
        this.testClasses = testClasses;
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
        for (TestClass testClass : testClasses) {
            if (testClass.shouldExecuteOnlyThis() || !testClass.shouldExecuteAll()) {
                return false;
            }
        }
        return true;
    }
}
