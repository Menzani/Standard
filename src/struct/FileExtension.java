package eu.menzani.struct;

import java.io.File;
import java.nio.file.Path;

public class FileExtension {
    public static final FileExtension SO = new FileExtension("so");
    public static final FileExtension DLL = new FileExtension("dll");
    public static final FileExtension C = new FileExtension("c");
    public static final FileExtension CPP = new FileExtension("cpp");
    public static final FileExtension ZIP = new FileExtension("zip");
    public static final FileExtension JAR = new FileExtension("jar");
    public static final FileExtension PDF = new FileExtension("pdf");

    private final String suffix;
    private final String suffixWithDot;

    public static FileExtension ofFile(Path file) {
        String fileName = file.getFileName().toString();
        int lastIndexOfDot = lastIndexOfDot(fileName);
        return new FileExtension(fileName.substring(lastIndexOfDot + 1), fileName.substring(lastIndexOfDot));
    }

    public static String getFileNameWithoutExtension(Path file) {
        return getFileNameWithoutExtension(file.getFileName().toString());
    }

    public static String getFileNameWithoutExtension(String fileName) {
        return fileName.substring(0, lastIndexOfDot(fileName));
    }

    private static int lastIndexOfDot(String fileName) {
        int result = fileName.lastIndexOf('.');
        if (result == -1) {
            throw new IllegalArgumentException("file does not have an extension.");
        }
        return result;
    }

    public FileExtension(String suffix) {
        this(suffix, '.' + suffix);
    }

    private FileExtension(String suffix, String suffixWithDot) {
        this.suffix = suffix;
        this.suffixWithDot = suffixWithDot;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getSuffixWithDot() {
        return suffixWithDot;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        FileExtension that = (FileExtension) object;
        return suffix.equals(that.suffix);
    }

    @Override
    public int hashCode() {
        return suffix.hashCode();
    }

    public static boolean endsWith(File file, FileExtension... extensions) {
        return endsWith(file.getName(), extensions);
    }

    public static boolean endsWith(Path file, FileExtension... extensions) {
        return endsWith(file.getFileName().toString(), extensions);
    }

    public static boolean endsWith(String fileName, FileExtension... extensions) {
        for (FileExtension extension : extensions) {
            if (fileName.endsWith(extension.getSuffixWithDot())) {
                return true;
            }
        }
        return false;
    }
}
