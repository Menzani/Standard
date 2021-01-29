package eu.menzani.test;

import eu.menzani.misc.Arrays;
import org.apache.bcel.classfile.JavaClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ParsedClassFile implements Comparable<ParsedClassFile> {
    private final JavaClass parsed;
    private final Path file;

    ParsedClassFile(JavaClass parsed, Path file) {
        this.parsed = parsed;
        this.file = file;
    }

    boolean isTestClass() {
        return !parsed.isInterface() && !parsed.isAbstract() && !parsed.isNested() && parsed.isPublic() &&
                parsed.getClassName().endsWith("Test");
    }

    String getClassName() {
        return parsed.getClassName();
    }

    byte[] readBytecode() throws IOException {
        return Files.readAllBytes(file);
    }

    @Override
    public boolean equals(Object other) {
        ParsedClassFile that = (ParsedClassFile) other;
        return parsed.equals(that.parsed);
    }

    @Override
    public int hashCode() {
        return parsed.hashCode();
    }

    @Override
    public int compareTo(ParsedClassFile that) {
        if (parsed.equals(that.parsed)) {
            return 0;
        }
        String thatClassName = that.parsed.getClassName();
        if (parsed.getSuperclassName().equals(thatClassName) || Arrays.contains(parsed.getInterfaceNames(), thatClassName)) {
            return 1;
        }
        return -1;
    }
}
