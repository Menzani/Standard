package eu.menzani.misc;

import eu.menzani.build.CompileCpp;
import eu.menzani.lang.Assert;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.FileExtension;
import eu.menzani.struct.Paths;
import eu.menzani.struct.Strings;
import eu.menzani.system.ApplicationProperty;
import eu.menzani.system.PlatformDependantValue;
import eu.menzani.system.PlatformFamilyDependantValue;
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
        String name = new LibraryName().get();
        String extension = new LibraryFileExtension().get().getSuffixWithDot();
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

    private class LibraryName extends PlatformDependantValue<String> {
        @Override
        protected String onWindows32() {
            return Strings.firstLetterToUppercase(lowercaseModuleName) + "_32";
        }

        @Override
        protected String onWindows64() {
            return Strings.firstLetterToUppercase(lowercaseModuleName) + "_64";
        }

        @Override
        protected String onLinux32() {
            return "lib" + lowercaseModuleName + "_32";
        }

        @Override
        protected String onLinux64() {
            return "lib" + lowercaseModuleName + "_64";
        }
    }

    private static class LibraryFileExtension extends PlatformFamilyDependantValue<FileExtension> {
        @Override
        protected FileExtension onWindows() {
            return FileExtension.DLL;
        }

        @Override
        protected FileExtension onLinux() {
            return FileExtension.SO;
        }
    }

    private Path libraryDirectory() {
        return new ApplicationProperty(clazz, "nativeLibrary", "directory").getAsPathOr(SystemPaths.TEMP_FOLDER);
    }
}
