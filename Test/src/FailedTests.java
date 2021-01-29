package eu.menzani.test;

import eu.menzani.io.ListFile;
import eu.menzani.system.Paths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class FailedTests {
    private final ListFile file;

    FailedTests(int id) throws IOException {
        file = new ListFile(Paths.TEMP_FOLDER, "failed-tests_" + id, StandardCharsets.ISO_8859_1).deleteOnEmpty();
        file.read();
    }

    boolean shouldExecuteOnlyFailed() {
        return file.isNotEmpty();
    }

    boolean didFail(TestElement testElement) {
        String testElementToString = testElement.toString();
        for (String line : file.lines()) {
            if (line.equals(testElementToString)) {
                return true;
            }
        }
        return false;
    }

    void add(TestElement testElement) {
        file.builder.append(testElement.toString());
        file.next();
    }

    void save() throws IOException {
        file.close();
    }
}
