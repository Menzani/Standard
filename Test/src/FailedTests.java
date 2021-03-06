package eu.menzani.test;

import eu.menzani.struct.ListFile;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class FailedTests {
    private final ListFile file;

    FailedTests(int id) throws IOException {
        file = new ListFile(SystemPaths.TEMP_FOLDER, "failed-tests_" + id, StandardCharsets.ISO_8859_1).deleteOnEmpty();
        file.read();
    }

    boolean shouldExecuteOnlyFailed() {
        return file.isNotEmpty();
    }

    boolean contains(TestElement testElement) {
        return file.contains(testElement.toString());
    }

    synchronized void add(TestElement testElement) {
        file.builder.append(testElement.toString());
        file.next();
    }

    synchronized void save() throws IOException {
        file.close();
    }
}
