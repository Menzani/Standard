package eu.menzani.system;

import java.nio.file.Path;

public class SystemPaths {
    public static final Path TEMP_FOLDER = new SystemProperty("java", "io", "tmpdir").getAsPath();
    public static final Path USER_FOLDER = new SystemProperty("user", "home").getAsPath();

    public static final Path DOWNLOADS_FOLDER = USER_FOLDER.resolve("Downloads");
    public static final Path JAVA_RUNTIME = SystemProperty.JAVA_HOME.resolve("bin").resolve("java");
    public static final Path WORKING_DIRECTORY = Path.of("").toAbsolutePath();

    public static boolean isWorkingDirectory(Path path) {
        return path.toAbsolutePath().equals(WORKING_DIRECTORY);
    }
}
