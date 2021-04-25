package eu.menzani.test;

import eu.menzani.benchmark.Stopwatch;
import eu.menzani.build.IdeaModule;
import eu.menzani.build.IdeaProject;
import eu.menzani.collection.ArrayBuilder;
import eu.menzani.lang.MethodHandles;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

class Runner {
    public static void main(String[] args) throws Exception {
        Stopwatch stopwatch = new Stopwatch();

        Runner runner = new Runner();
        runner.scan();
        runner.buildIndex();
        runner.loadFailedTests();
        runner.run(stopwatch);
        runner.saveFailedTests();

        stopwatch.stop();
    }

    private final Scanner scanner = new Scanner();
    private Index index;
    private FailedTests failedTests;
    private WorkerManager workerManager;

    private Runner() {
    }

    private void scan() throws IOException {
        for (IdeaModule module : IdeaProject.current().getModules()) {
            Path testOutputDirectory = module.getTestOutputDirectory();
            if (Files.exists(testOutputDirectory)) {
                Files.walkFileTree(testOutputDirectory, scanner);
            }
        }
    }

    private void buildIndex() throws ReflectiveOperationException {
        scanner.loadClasses(Runner.class);

        var indexBuilder = new ArrayBuilder<>(scanner.getTestClasses(), TestClass.class);
        for (ParsedClassFile classFile : indexBuilder) {
            Class<?> clazz = classFile.getLoadedClass();

            var testMethodsBuilder = new ArrayBuilder<>(clazz.getMethods(), TestMethod.class);
            for (Method method : testMethodsBuilder) {
                if (method.getDeclaringClass() == Object.class) continue;
                testMethodsBuilder.add(new TestMethod(method));
            }

            MethodHandle constructor = MethodHandles.PUBLIC_LOOKUP.findConstructor(clazz, MethodHandles.VOID_VOID_METHOD_TYPE);
            TestClass testClass = new TestClass(clazz, constructor, testMethodsBuilder.build());
            testClass.initTestMethods();
            indexBuilder.add(testClass);
        }

        index = new Index(indexBuilder.build());
        index.validate();
    }

    private void loadFailedTests() throws IOException {
        failedTests = new FailedTests(index.hashCode());

        if (failedTests.shouldExecuteOnlyFailed() && index.shouldExecuteAll()) {
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

            if (failedTests.shouldExecuteOnlyFailed()) {
                stopwatch.disableReport();
            } else {
                stopwatch.setMinimumToReport(1_000_000_000L * 5L);
                stopwatch.setPrefix("Completed");
            }
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

            stopwatch.disableReport();
            workerManager.setPrintCurrentTestMethod(true);
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
