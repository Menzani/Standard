package eu.menzani.system;

import eu.menzani.lang.Optional;

import java.nio.file.Path;

public class SystemPaths {
    public static final Path TEMP_FOLDER = new SystemProperty("java.io.tmpdir").getAsPath();
    public static final Path USER_FOLDER = new SystemProperty("user.home").getAsPath();
    public static final @Optional Path JAVA_HOME = new SystemProperty("java.home").getAsPathOrNull();

    public static final Path DOWNLOADS_FOLDER = USER_FOLDER.resolve("Downloads");
}