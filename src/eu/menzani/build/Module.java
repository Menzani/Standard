package eu.menzani.build;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Module {
    private final Path directory;
    private final String name;
    private final String artifactName;
    private final List<JarSource> sourceFolders;
    private Path productionOutputDirectory;

    Module(Path directory, String name, String artifactName, List<JarSource> sourceFolders) {
        this.directory = directory;
        this.name = name;
        this.artifactName = artifactName;
        this.sourceFolders = sourceFolders;
    }

    void computeOutputDirectory(Path projectOutputDirectory) throws IOException {
        ModuleScanner moduleScanner = new ModuleScanner(name);
        Files.walkFileTree(projectOutputDirectory, moduleScanner);
        productionOutputDirectory = moduleScanner.getProductionOutputAbsolutePath();
    }

    Path getDirectory() {
        return directory;
    }

    String getName() {
        return name;
    }

    String getArtifactName() {
        return artifactName;
    }

    List<JarSource> getSourceFolders() {
        return sourceFolders;
    }

    Path getProductionOutputDirectory() {
        return productionOutputDirectory;
    }
}
