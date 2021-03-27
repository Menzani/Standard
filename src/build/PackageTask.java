package eu.menzani.build;

import eu.menzani.lang.UncaughtException;
import eu.menzani.system.Platform;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class PackageTask {
    public static void main(String[] args) throws Exception {
        PackageTask packageTask = new PackageTask();
        packageTask.packageCurrentProject();
    }

    private static final Path outputPath = findOutputPath();

    private static Path findOutputPath() {
        IdeaProject project = IdeaProject.current();
        final Path ramdisk = Path.of("R:", project.getName());
        try {
            try {
                Files.createDirectories(ramdisk);
                return ramdisk;
            } catch (FileSystemException e) {
                if (e.getReason().equals("Unable to determine if root directory exists")) {
                    return project.getDirectory();
                }
                throw e;
            }
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    private PackageTask() {
    }

    private void packageCurrentProject() throws Exception {
        IdeaProject project = IdeaProject.current();
        for (IdeaModule module : project.getModules()) {
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
