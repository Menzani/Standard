package eu.menzani;

import eu.menzani.lang.UncaughtException;
import eu.menzani.system.ApplicationProperty;
import eu.menzani.system.Platform;
import eu.menzani.system.PlatformNotSupportedException;
import eu.menzani.system.SystemProperties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Native {
    public static void init() {
    }

    static {
        String libraryName = libraryName();
        Path libraryPath = libraryDirectory().resolve(libraryName);

        try (InputStream stream = Native.class.getResourceAsStream(libraryName)) {
            Files.copy(stream, libraryPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        System.load(libraryPath.toAbsolutePath().toString());
    }

    private static String libraryName() {
        switch (Platform.current()) {
            case LINUX_32:
                return "libstandard_32.so";
            case LINUX_64:
                return "libstandard_64.so";
            case WINDOWS_32:
                return "Standard_32.dll";
            case WINDOWS_64:
                return "Standard_64.dll";
        }
        throw new PlatformNotSupportedException();
    }

    private static Path libraryDirectory() {
        return new ApplicationProperty(Native.class, "nativeLibrary", "directory")
                .getAsPathOr(() -> SystemProperties.TEMP_FOLDER);
    }
}
