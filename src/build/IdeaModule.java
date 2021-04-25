package eu.menzani.build;

import java.nio.file.Path;
import java.util.List;

public class IdeaModule {
    public static final Path PRODUCTION_FOLDER = Path.of("production");
    static final String NATIVE_FOLDER_NAME = "native";
    private static final Path testFolder = Path.of("test");
    private static final Path nativeFolder = Path.of(NATIVE_FOLDER_NAME);

    private final String name;
    private final Path nameAsFolder;
    private final String artifactName;
    private final List<JarSource> javaSourceFolders;
    private final Path nativeSourceFolder;

    private Path productionOutputDirectory;
    private Path testOutputDirectory;
    private Path nativeOutputDirectory;

    IdeaModule(Path directory, String name, Path nameAsFolder, String artifactName, List<JarSource> javaSourceFolders) {
        this.name = name;
        this.nameAsFolder = nameAsFolder;
        this.artifactName = artifactName;
        this.javaSourceFolders = javaSourceFolders;
        nativeSourceFolder = directory.resolve(nativeFolder);
    }

    void computeOutputDirectories(Path projectOutputDirectory) {
        productionOutputDirectory = projectOutputDirectory.resolve(PRODUCTION_FOLDER).resolve(nameAsFolder);
        testOutputDirectory = projectOutputDirectory.resolve(testFolder).resolve(nameAsFolder);
        nativeOutputDirectory = projectOutputDirectory.resolve(nativeFolder).resolve(nameAsFolder);
    }

    String getName() {
        return name;
    }

    Path getNameAsFolder() {
        return nameAsFolder;
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

    public Path getTestOutputDirectory() {
        return testOutputDirectory;
    }

    Path getNativeOutputDirectory() {
        return nativeOutputDirectory;
    }
}
