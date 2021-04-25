package eu.menzani.misc;

import eu.menzani.Standard;
import eu.menzani.build.CompileCppTask;
import eu.menzani.lang.Assert;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.AppInfo;
import eu.menzani.struct.FileExtension;
import eu.menzani.struct.Paths;
import eu.menzani.system.ApplicationProperty;
import eu.menzani.system.PlatformDependantValue;
import eu.menzani.system.PlatformFamilyDependantValue;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public class NativeLibrary {
    public static final String FOLDER_IN_ARTIFACT = "native";

    private final long versionId;
    private final Module module;

    public NativeLibrary(long versionId, Module module) {
        this.versionId = versionId;
        this.module = module;
    }

    public void load() {
        String name = new LibraryName().get();
        String extension = new LibraryFileExtension().get().getSuffixWithDot();
        Path path = libraryDirectory().resolve(name + '_' + versionId + extension);

        String resourceName = FOLDER_IN_ARTIFACT + '/' + name + extension;
        URL resource = ClassLoader.getSystemResource(resourceName);
        boolean isIDEForSure = (resource == null);
        if (isIDEForSure) {
            if (Standard.isNotLibrary()) {
                CompileCppTask.run(true);
                resource = ClassLoader.getSystemResource(resourceName);
            } else {
                throw new NativeLibraryException(module);
            }
        }
        String protocol = resource.getProtocol();
        if (!isIDEForSure && protocol.equals("jar")) {
            extractFromJar(path, resource);
        } else {
            Assert.equal(protocol, "file");
            if (!isIDEForSure && Standard.isNotLibrary()) {
                CompileCppTask.run(false);
            }
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
            return onWindows("_32");
        }

        @Override
        protected String onWindows64() {
            return onWindows("_64");
        }

        private String onWindows(String suffix) {
            return AppInfo.getModuleDisplayName(module) + suffix;
        }

        @Override
        protected String onLinux32() {
            return onLinux("_32");
        }

        @Override
        protected String onLinux64() {
            return onLinux("_64");
        }

        private String onLinux(String suffix) {
            return "lib" + AppInfo.getModuleDisplayName(module).toLowerCase(Locale.ENGLISH) + suffix;
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
        return new ApplicationProperty(module, "nativeLibrary", "directory").getAsPathOr(SystemPaths.TEMP_FOLDER);
    }
}
