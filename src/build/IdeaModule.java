package eu.menzani.build;

import java.nio.file.Path;
import java.util.List;

public class IdeaModule {
    public static final Path PRODUCTION_OUTPUT_FOLDER = Path.of("production");
    static final String NATIVE_OUTPUT_FOLDER_NAME = "native";
    private static final Path nativeOutputFolder = Path.of(NATIVE_OUTPUT_FOLDER_NAME);

    private final String name;
    private final String artifactName;
    private final List<JarSource> javaSourceFolders;
    private final Path nativeSourceFolder;
    private Path productionOutputDirectory;
    private Path nativeOutputDirectory;

    IdeaModule(Path directory, String name, String artifactName, List<JarSource> javaSourceFolders) {
        this.name = name;
        this.artifactName = artifactName;
        this.javaSourceFolders = javaSourceFolders;
        nativeSourceFolder = directory.resolve(nativeOutputFolder);
    }

    void computeOutputDirectories(Path projectOutputDirectory) {
        productionOutputDirectory = projectOutputDirectory.resolve(PRODUCTION_OUTPUT_FOLDER).resolve(name);
        nativeOutputDirectory = projectOutputDirectory.resolve(nativeOutputFolder).resolve(name);
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
