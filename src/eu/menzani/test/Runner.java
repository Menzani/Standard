package eu.menzani.test;

import eu.menzani.lang.ArrayBuilder;
import eu.menzani.system.SystemProperty;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Runner {
    public static void main(String[] args) throws Exception {
        long start = System.nanoTime();

        Runner runner = new Runner();
        runner.scan(SystemProperty.CLASS_PATH);
        runner.scan(SystemProperty.MODULE_PATH);
        runner.buildIndex();
        runner.run();

        long end = System.nanoTime();
        long elapsed = (end - start) / 1_000_000_000L;
        if (elapsed > 5L) {
            System.out.println("Completed in " + formatExecutionTime(elapsed));
        }
    }

    private static String formatExecutionTime(long seconds) {
        long secondsPart = seconds % 60L;
        long minutesPart = (seconds / 60L) % 60L;
        long hoursPart = (seconds / (60L * 60L)) % 24L;
        long daysPart = seconds / (60L * 60L * 24L);
        String text = "";
        if (daysPart != 0L) text += daysPart + "d ";
        if (hoursPart != 0L) text += hoursPart + "h ";
        if (minutesPart != 0L) text += minutesPart + "m ";
        return text + secondsPart + 's';
    }

    private final Scanner scanner = new Scanner();
    private Index index;

    private void scan(List<Path> paths) throws IOException {
        for (Path path : paths) {
            if (Files.isDirectory(path)) {
                scanner.setStartIndex(path.toString().length() + 1);
                Files.walkFileTree(path, scanner);
            }
        }
    }

    private void buildIndex() {
        ArrayBuilder<String, TestClass> indexBuilder = new ArrayBuilder<>(scanner.getClassNames(), TestClass.class);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        indexBuilder.map(className -> {
            Class<?> clazz = Class.forName(className, false, classLoader);
            ArrayBuilder<Method, TestMethod> testMethodsBuilder = new ArrayBuilder<>(clazz.getMethods(), TestMethod.class);
            testMethodsBuilder.filter(method -> method.getDeclaringClass() == Object.class);
            testMethodsBuilder.map(TestMethod::new);
            TestClass testClass = new TestClass(clazz, className, clazz.getConstructor(), testMethodsBuilder.build());
            testClass.initTestMethods();
            return testClass;
        });
        index = new Index(indexBuilder.build());
        index.validate();
    }

    private void run() throws ReflectiveOperationException {
        if (index.shouldExecuteAll()) {
            for (TestClass testClass : index.getTestClasses()) {
                run(testClass);
            }
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
        }
    }

    private static void run(TestClass testClass) throws ReflectiveOperationException {
        for (TestMethod testMethod : testClass.getTestMethods()) {
            if (run(testClass, testMethod)) {
                break;
            }
        }
    }

    private static boolean run(TestClass testClass, TestMethod testMethod) throws ReflectiveOperationException {
        Object instance;
        try {
            instance = testClass.getConstructor().newInstance();
        } catch (InvocationTargetException | ExceptionInInitializerError e) {
            System.err.println(testClass);
            e.getCause().printStackTrace();
            return true;
        }
        try {
            testMethod.getReflected().invoke(instance);
        } catch (InvocationTargetException e) {
            System.err.println(testMethod);
            e.getCause().printStackTrace();
        }
        return false;
    }
}
