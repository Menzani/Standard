package eu.menzani.test;

import eu.menzani.benchmark.Stopwatch;
import eu.menzani.collection.ArrayBuilder;
import eu.menzani.system.ExtensibleClassLoader;
import eu.menzani.system.Paths;
import eu.menzani.system.SystemProperty;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

class Runner {
    public static void main(String[] args) throws Exception {
        Stopwatch stopwatch = new Stopwatch();

        Runner runner = new Runner();
        runner.scan(SystemProperty.CLASS_PATH);
        runner.scan(SystemProperty.MODULE_PATH);
        runner.buildIndex();
        runner.loadFailedTests();
        runner.run(stopwatch);
        runner.saveFailedTests();

        stopwatch.stop();
    }

    private static final Path ideaProductionOutputPath = Path.of("production");
    private static final Path ideaTestOutputPath = Path.of("test");

    private static final MethodHandles.Lookup lookup = MethodHandles.publicLookup();
    private static final MethodType emptyConstructorMethodType = MethodType.methodType(void.class);

    private final Scanner scanner = new Scanner();
    private Index index;
    private FailedTests failedTests;

    private void scan(Set<Path> paths) throws IOException {
        for (Path path : paths) {
            if (Paths.isWorkingDirectory(path)) continue;
            int indexOfIdeaProductionOutputPath = Paths.indexOf(path, ideaProductionOutputPath);
            if (indexOfIdeaProductionOutputPath != -1) {
                Path ideaModuleTestOutputPath = Paths.replace(path, indexOfIdeaProductionOutputPath, ideaTestOutputPath);
                scanner.setStartIndex(ideaModuleTestOutputPath.toString().length() + 1);
                Files.walkFileTree(ideaModuleTestOutputPath, scanner);
            }
        }
    }

    private void buildIndex() throws ReflectiveOperationException {
        ExtensibleClassLoader classLoader = scanner.getClassLoader();

        ArrayBuilder<String, TestClass> indexBuilder = new ArrayBuilder<>(classLoader.getLoadedClassNames(), TestClass.class);
        for (String className : indexBuilder.collection()) {
            Class<?> clazz = Class.forName(className, false, classLoader);

            ArrayBuilder<Method, TestMethod> testMethodsBuilder = new ArrayBuilder<>(clazz.getMethods(), TestMethod.class);
            for (Method method : testMethodsBuilder.array()) {
                if (method.getDeclaringClass() == Object.class) continue;
                testMethodsBuilder.add(new TestMethod(method));
            }

            TestClass testClass = new TestClass(clazz, className, lookup.findConstructor(clazz, emptyConstructorMethodType), testMethodsBuilder.build());
            testClass.initTestMethods();
            indexBuilder.add(testClass);
        }

        index = new Index(indexBuilder.build());
        index.validate();
    }

    private void loadFailedTests() throws IOException {
        failedTests = new FailedTests(index.hashCode());

        if (failedTests.shouldExecuteOnlyFailed()) {
            for (TestClass testClass : index.getTestClasses()) {
                boolean containsFailed = false;
                for (TestMethod testMethod : testClass.getTestMethods()) {
                    if (failedTests.didNotFail(testMethod)) {
                        testMethod.disable();
                    } else {
                        containsFailed = true;
                    }
                }
                if (!containsFailed && failedTests.didNotFail(testClass)) {
                    testClass.disable();
                }
            }
        }
    }

    private void run(Stopwatch stopwatch) throws ReflectiveOperationException {
        if (index.shouldExecuteAll()) {
            for (TestClass testClass : index.getTestClasses()) {
                run(testClass);
            }
            stopwatch.setPrefix(failedTests.shouldExecuteOnlyFailed() ? "Re-ran failed tests in " : "Completed in ");
        } else {
            for (TestClass testClass : index.getTestClasses()) {
                if (testClass.shouldExecuteOnlyThis()) {
                    run(testClass);
                } else {
                    for (TestMethod testMethod : testClass.getTestMethods()) {
                        if (testMethod.shouldExecuteOnlyThis()) {
                            run(testClass, testMethod);
                        }
                    }
                }
            }
            stopwatch.setPrefix("Executed some in ");
        }
    }

    private void run(TestClass testClass) throws ReflectiveOperationException {
        if (testClass.isDisabled()) return;
        for (TestMethod testMethod : testClass.getTestMethods()) {
            if (run(testClass, testMethod)) {
                break;
            }
        }
    }

    private boolean run(TestClass testClass, TestMethod testMethod) throws ReflectiveOperationException {
        if (testMethod.isDisabled()) return false;
        Object instance;
        try {
            instance = testClass.getConstructor().invoke();
        } catch (Throwable e) {
            System.err.println(testClass);
            if (e instanceof ExceptionInInitializerError) {
                e = e.getCause();
            }
            e.printStackTrace();
            failedTests.add(testClass);
            return true;
        }
        try {
            testMethod.getReflected().invoke(instance);
        } catch (InvocationTargetException e) {
            System.err.println(testMethod);
            e.getCause().printStackTrace();
            failedTests.add(testMethod);
        }
        return false;
    }

    private void saveFailedTests() throws IOException {
        if (failedTests.shouldExecuteOnlyFailed() && !index.shouldExecuteAll()) {
            return;
        }
        failedTests.save();
    }
}
