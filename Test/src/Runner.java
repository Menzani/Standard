package eu.menzani.test;

import eu.menzani.benchmark.Stopwatch;
import eu.menzani.build.Module;
import eu.menzani.collection.ArrayBuilder;
import eu.menzani.struct.Paths;
import eu.menzani.system.SystemPaths;
import eu.menzani.system.SystemProperty;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
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

    private static final Path ideaProductionOutputPath = Path.of(Module.PRODUCTION_FOLDER_NAME);
    private static final Path ideaTestOutputPath = Path.of("test");

    private static final MethodHandles.Lookup lookup = MethodHandles.publicLookup();
    private static final MethodType emptyConstructorMethodType = MethodType.methodType(void.class);

    private final Scanner scanner = new Scanner();
    private Index index;
    private FailedTests failedTests;
    private WorkerManager workerManager;

    private Runner() {
    }

    private void scan(Set<Path> paths) throws IOException {
        for (Path path : paths) {
            if (SystemPaths.isWorkingDirectory(path)) continue;
            int indexOfIdeaProductionOutputPath = Paths.indexOf(path, ideaProductionOutputPath);
            if (indexOfIdeaProductionOutputPath != -1) {
                Path ideaModuleTestOutputPath = Paths.replace(path, indexOfIdeaProductionOutputPath, ideaTestOutputPath);
                if (Files.exists(ideaModuleTestOutputPath)) {
                    Files.walkFileTree(ideaModuleTestOutputPath, scanner);
                }
            }
        }
    }

    private void buildIndex() throws ReflectiveOperationException {
        scanner.loadClasses(getClass());

        ArrayBuilder<ParsedClassFile, TestClass> indexBuilder = new ArrayBuilder<>(scanner.getTestClasses(), TestClass.class);
        for (ParsedClassFile classFile : indexBuilder) {
            Class<?> clazz = classFile.getLoadedClass();

            ArrayBuilder<Method, TestMethod> testMethodsBuilder = new ArrayBuilder<>(clazz.getMethods(), TestMethod.class);
            for (Method method : testMethodsBuilder) {
                if (method.getDeclaringClass() == Object.class) continue;
                testMethodsBuilder.add(new TestMethod(method));
            }

            MethodHandle constructor = lookup.findConstructor(clazz, emptyConstructorMethodType);
            TestClass testClass = new TestClass(clazz, constructor, testMethodsBuilder.build());
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
                if (failedTests.contains(testClass)) {
                    continue;
                }

                boolean containsFailed = false;
                for (TestMethod testMethod : testClass.getTestMethods()) {
                    if (failedTests.contains(testMethod)) {
                        containsFailed = true;
                    } else {
                        testMethod.disable();
                    }
                }

                if (!containsFailed) {
                    testClass.disable();
                }
            }
        }
    }

    private void run(Stopwatch stopwatch) {
        workerManager = new WorkerManager(failedTests);

        if (index.shouldExecuteAll()) {
            for (TestClass testClass : index.getTestClasses()) {
                enqueue(testClass);
            }
            stopwatch.setPrefix(failedTests.shouldExecuteOnlyFailed() ? "Re-ran failed tests in " : "Completed in ");
        } else {
            for (TestClass testClass : index.getTestClasses()) {
                if (testClass.shouldExecuteOnlyThis()) {
                    enqueue(testClass);
                } else {
                    for (TestMethod testMethod : testClass.getTestMethods()) {
                        if (testMethod.shouldExecuteOnlyThis()) {
                            enqueue(testMethod);
                        }
                    }
                }
            }
            stopwatch.setPrefix("Executed some in ");
        }

        workerManager.run(Runtime.getRuntime().availableProcessors());
    }

    private void enqueue(TestClass testClass) {
        if (testClass.isEnabled()) {
            for (TestMethod testMethod : testClass.getTestMethods()) {
                enqueue(testMethod);
            }
        }
    }

    private void enqueue(TestMethod testMethod) {
        if (testMethod.isEnabled()) {
            workerManager.enqueue(testMethod);
        }
    }

    private void saveFailedTests() throws IOException {
        if (failedTests.shouldExecuteOnlyFailed() && !index.shouldExecuteAll()) {
            return;
        }
        failedTests.save();
    }
}
