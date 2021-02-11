package eu.menzani.misc;

import eu.menzani.build.CompileCpp;
import eu.menzani.lang.Assert;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.FileExtension;
import eu.menzani.struct.Paths;
import eu.menzani.struct.Strings;
import eu.menzani.system.ApplicationProperty;
import eu.menzani.system.Platform;
import eu.menzani.system.PlatformNotSupportedException;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class NativeLibrary {
    public static final String FOLDER_IN_ARTIFACT = "native";

    private final long versionId;
    private final Class<?> clazz;
    private final String lowercaseModuleName;

    public NativeLibrary(long versionId, Class<?> clazz, String lowercaseModuleName) {
        this.versionId = versionId;
        this.clazz = clazz;
        this.lowercaseModuleName = lowercaseModuleName;
    }

    public void load() {
        String name = libraryName();
        FileExtension fileExtension = libraryFileExtension();
        String extension = fileExtension.getSuffixWithDot();
        Path path = libraryDirectory().resolve(name + '_' + versionId + extension);

        String resourceName = FOLDER_IN_ARTIFACT + '/' + name + extension;
        URL resource = ClassLoader.getSystemResource(resourceName);
        boolean isIDE = (resource == null);
        if (isIDE) {
            CompileCpp.run();
            resource = ClassLoader.getSystemResource(resourceName);
        }
        String protocol = resource.getProtocol();
        if (!isIDE && protocol.equals("jar")) {
            extractFromJar(path, resource);
        } else {
            Assert.equal(protocol, "file");
            path = Paths.fromURL(resource);
        }

        System.load(path.toAbsolutePath().toString());
    }

    private static void extractFromJar(Path path, URL url) {
        if (Files.notExists(path)) {
            try (InputStream stream = url.openStream()) {
                Files.copy(stream, path);
            } catch (IOException e) {
                throw new UncaughtException(e);
            }
        }
    }

    private String libraryName() {
        switch (Platform.current()) {
            case LINUX_32:
                return "lib" + lowercaseModuleName + "_32";
            case LINUX_64:
                return "lib" + lowercaseModuleName + "_64";
            case WINDOWS_32:
                return Strings.firstLetterToUppercase(lowercaseModuleName) + "_32";
            case WINDOWS_64:
                return Strings.firstLetterToUppercase(lowercaseModuleName) + "_64";
            case MAC_32:
            case MAC_64:
                throw new PlatformNotSupportedException();
            default:
                throw new AssertionError();
        }
    }

    private static FileExtension libraryFileExtension() {
        switch (Platform.current().getFamily()) {
            case WINDOWS:
                return FileExtension.DLL;
            case LINUX:
                return FileExtension.SO;
            case MAC:
                throw new PlatformNotSupportedException();
            default:
                throw new AssertionError();
        }
    }

    private Path libraryDirectory() {
        return new ApplicationProperty(clazz, "nativeLibrary", "directory").getAsPathOr(SystemPaths.TEMP_FOLDER);
    }
}
