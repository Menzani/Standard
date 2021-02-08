package eu.menzani.build;

import eu.menzani.misc.XmlParser;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Project {
    private static final Path directory = SystemPaths.WORKING_DIRECTORY;
    private static final String name = directory.getFileName().toString();

    private final List<Module> modules;

    Project() throws IOException {
        ProjectDescriptorScanner projectDescriptorScanner = new ProjectDescriptorScanner();
        XmlParser.parse(Path.of(".idea", "misc.xml"), projectDescriptorScanner);
        Path projectOutputDirectory = projectDescriptorScanner.getOutputDirectory();

        ModulesScanner modulesScanner = new ModulesScanner(name);
        Files.walkFileTree(directory, modulesScanner);
        modules = modulesScanner.getModules(projectOutputDirectory);
    }

    List<Module> getModules() {
        return modules;
    }
}
