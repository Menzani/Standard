package eu.menzani.build;

import eu.menzani.lang.UncaughtException;
import eu.menzani.system.Platform;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Package {
    public static void main(String[] args) throws Exception {
        Package package_ = new Package();
        package_.package_();
    }

    private static final Path outputPath = findOutputPath();

    private static Path findOutputPath() {
        final Path ramdisk = Path.of("R:", Project.NAME);
        try {
            try {
                Files.createDirectories(ramdisk);
                return ramdisk;
            } catch (FileSystemException e) {
                if (e.getReason().equals("Unable to determine if root directory exists")) {
                    return Project.DIRECTORY;
                }
                throw e;
            }
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    private final Project project;

    private Package() throws IOException {
        project = new Project();
    }

    private void package_() throws Exception {
        for (Module module : project.getModules()) {
            Path productionOutputDirectory = module.getProductionOutputDirectory();
            String artifactName = module.getArtifactName();
            if (Files.exists(productionOutputDirectory)) {
                createJar(JarSource.single(productionOutputDirectory), artifactName);
            } else {
                System.out.println("Module has not been compiled (skipping): " + module.getName());
            }
            createJar(module.getJavaSourceFolders(), artifactName + "-sources");
        }
    }

    private void createJar(List<JarSource> sources, String fileName) throws Exception {
        Path file = outputPath.resolve(fileName + ".jar");
        String fileToString = file.toString();
        if (Platform.current().isWindows()) {
            fileToString = fileToString.replace('\\', '/');
        }

        Files.deleteIfExists(file);
        try (JarFileCreator jarFileCreator = new JarFileCreator(fileToString)) {
            for (JarSource source : sources) {
                jarFileCreator.source = source;
                Files.walkFileTree(source.getPath(), jarFileCreator);
            }
        }
    }
}
