package eu.menzani.build;

import eu.menzani.misc.XmlParser;
import eu.menzani.system.Paths;
import eu.menzani.system.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Build {
    public static void main(String[] args) throws Exception {
        Build build = new Build();
        build.scan();
        build.build();
    }

    private static final Path output = Path.of("R:");

    private List<Module> modules;

    private Build() {
    }

    private void scan() throws IOException {
        ProjectDescriptorScanner projectDescriptorScanner = new ProjectDescriptorScanner();
        XmlParser.parse(Path.of(".idea", "misc.xml"), projectDescriptorScanner);
        Path projectOutputDirectory = projectDescriptorScanner.getOutputDirectory();

        ModulesScanner modulesScanner = new ModulesScanner();
        Files.walkFileTree(Paths.WORKING_DIRECTORY, modulesScanner);
        modules = modulesScanner.getModules(projectOutputDirectory);
    }

    private void build() throws Exception {
        for (Module module : modules) {
            String artifactName = module.getArtifactName();
            createJar(JarSource.single(module.getProductionOutputDirectory()), artifactName);
            createJar(module.getSourceFolders(), artifactName + "-sources");
        }
    }

    private void createJar(List<JarSource> sources, String fileName) throws Exception {
        Path file = output.resolve(fileName + ".jar");
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
