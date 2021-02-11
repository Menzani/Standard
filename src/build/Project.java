package eu.menzani.build;

import eu.menzani.misc.XmlParser;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Project {
    static final Path DIRECTORY = SystemPaths.WORKING_DIRECTORY;
    static final String NAME = DIRECTORY.getFileName().toString();

    private final List<Module> modules;

    Project() throws IOException {
        ProjectDescriptorScanner projectDescriptorScanner = new ProjectDescriptorScanner();
        XmlParser.parse(Path.of(".idea", "misc.xml"), projectDescriptorScanner);
        Path projectOutputDirectory = projectDescriptorScanner.getOutputDirectory();

        ModulesScanner modulesScanner = new ModulesScanner(NAME);
        Files.walkFileTree(DIRECTORY, modulesScanner);
        modules = modulesScanner.getModules(projectOutputDirectory);
    }

    List<Module> getModules() {
        return modules;
    }
}
