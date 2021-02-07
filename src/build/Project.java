package eu.menzani.build;

import eu.menzani.misc.XmlParser;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Project {
    private final String name = SystemPaths.WORKING_DIRECTORY.getFileName().toString();
    private final List<Module> modules;

    Project() throws IOException {
        ProjectDescriptorScanner projectDescriptorScanner = new ProjectDescriptorScanner();
        XmlParser.parse(Path.of(".idea", "misc.xml"), projectDescriptorScanner);
        Path projectOutputDirectory = projectDescriptorScanner.getOutputDirectory();

        ModulesScanner modulesScanner = new ModulesScanner(name);
        Files.walkFileTree(SystemPaths.WORKING_DIRECTORY, modulesScanner);
        modules = modulesScanner.getModules(projectOutputDirectory);
    }

    String getName() {
        return name;
    }

    List<Module> getModules() {
        return modules;
    }
}
