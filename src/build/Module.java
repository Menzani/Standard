package eu.menzani.build;

import java.nio.file.Path;
import java.util.List;

public class Module {
    static final String NATIVE_FOLDER_NAME = "native";
    public static final String PRODUCTION_FOLDER_NAME = "production";

    private final String name;
    private final String artifactName;
    private final List<JarSource> javaSourceFolders;
    private final Path nativeSourceFolder;
    private Path productionOutputDirectory;
    private Path nativeOutputDirectory;

    Module(Path directory, String name, String artifactName, List<JarSource> javaSourceFolders) {
        this.name = name;
        this.artifactName = artifactName;
        this.javaSourceFolders = javaSourceFolders;
        nativeSourceFolder = directory.resolve(NATIVE_FOLDER_NAME);
    }

    void computeOutputDirectories(Path projectOutputDirectory) {
        productionOutputDirectory = projectOutputDirectory.resolve(PRODUCTION_FOLDER_NAME).resolve(name);
        nativeOutputDirectory = projectOutputDirectory.resolve(NATIVE_FOLDER_NAME).resolve(name);
    }

    String getName() {
        return name;
    }

    String getArtifactName() {
        return artifactName;
    }

    List<JarSource> getJavaSourceFolders() {
        return javaSourceFolders;
    }

    Path getNativeSourceFolder() {
        return nativeSourceFolder;
    }

    Path getProductionOutputDirectory() {
        return productionOutputDirectory;
    }

    Path getNativeOutputDirectory() {
        return nativeOutputDirectory;
    }
}
