package eu.menzani;

import eu.menzani.lang.UncaughtException;
import eu.menzani.system.ApplicationProperty;
import eu.menzani.system.Paths;
import eu.menzani.system.Platform;
import eu.menzani.system.PlatformNotSupportedException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Native {
    public static void init() {
    }

    static {
        LibraryInfo libraryInfo = libraryInfo();
        Path libraryPath = libraryDirectory().resolve(libraryInfo.getFileName());

        if (Files.notExists(libraryPath)) {
            try (InputStream stream = Native.class.getResourceAsStream(libraryInfo.getResourceName())) {
                Files.copy(stream, libraryPath);
            } catch (IOException e) {
                throw new UncaughtException(e);
            }
        }
        System.load(libraryPath.toAbsolutePath().toString());
    }

    private static LibraryInfo libraryInfo() {
        switch (Platform.current()) {
            case LINUX_32:
                return new LibraryInfo("libstandard_32", "so");
            case LINUX_64:
                return new LibraryInfo("libstandard_64", "so");
            case WINDOWS_32:
                return new LibraryInfo("Standard_32", "dll");
            case WINDOWS_64:
                return new LibraryInfo("Standard_64", "dll");
        }
        throw new PlatformNotSupportedException();
    }

    private static Path libraryDirectory() {
        return new ApplicationProperty(Native.class, "nativeLibrary", "directory")
                .getAsPathOr(() -> Paths.TEMP_FOLDER);
    }

    private static class LibraryInfo {
        private static final long versionId = 2278256207267942924L;

        private final String name;
        private final String extension;

        LibraryInfo(String name, String extension) {
            this.name = name;
            this.extension = '.' + extension;
        }

        String getResourceName() {
            return name + extension;
        }

        String getFileName() {
            return name + '_' + versionId + extension;
        }
    }
}
