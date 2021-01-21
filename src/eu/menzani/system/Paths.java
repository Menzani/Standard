package eu.menzani.system;

import eu.menzani.lang.Optional;

import java.nio.file.Path;

public class Paths {
    public static final Path TEMP_FOLDER = new SystemProperty("java.io.tmpdir").getAsPath();
    public static final Path USER_FOLDER = new SystemProperty("user.home").getAsPath();
    public static final @Optional Path JAVA_HOME = new SystemProperty("java.home").getAsPathOrNull();

    public static final Path WORKING_DIRECTORY = Path.of("").toAbsolutePath();
    public static final Path DOWNLOADS_FOLDER = USER_FOLDER.resolve("Downloads");

    public static boolean isWorkingDirectory(Path path) {
        return path.toAbsolutePath().equals(WORKING_DIRECTORY);
    }

    public static boolean contains(Path path, Path subPath) {
        return indexOf(path, subPath) != -1;
    }

    public static int indexOf(Path path, Path subPath) {
        for (int i = 0; i < path.getNameCount(); i++) {
            if (path.getName(i).equals(subPath)) {
                return i;
            }
        }
        return -1;
    }

    public static Path replace(Path path, int index, Path replacement) {
        Path before = path.subpath(0, index);
        Path after = path.subpath(index + 1, path.getNameCount());
        return path.getRoot().resolve(before).resolve(replacement).resolve(after);
    }
}
