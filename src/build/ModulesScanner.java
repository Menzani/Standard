package eu.menzani.build;

import eu.menzani.misc.XmlParser;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ModulesScanner extends SimpleFileVisitor<Path> {
    private final String projectName;
    private final List<Module> modules = new ArrayList<>();

    ModulesScanner(String projectName) {
        this.projectName = projectName;
    }

    List<Module> getModules(Path projectOutputDirectory) {
        for (Module module : modules) {
            module.computeOutputDirectories(projectOutputDirectory);
        }
        return Collections.unmodifiableList(modules);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        switch (dir.getFileName().toString()) {
            case "src":
            case "res":
            case "test":
            case "unit-test":
            case Module.NATIVE_OUTPUT_FOLDER_NAME:
            case "lib":
            case Project.IDEA_FOLDER_NAME:
            case ".git":
                return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".iml")) {
            Path directory = file.getParent();
            String name = directory.getFileName().toString();

            String artifactName;
            if (name.equals(projectName)) {
                artifactName = name;
            } else {
                artifactName = projectName + '-' + name;
            }

            ModuleDescriptorScanner moduleDescriptorScanner = new ModuleDescriptorScanner();
            XmlParser.parse(file, moduleDescriptorScanner);
            List<JarSource> javaSourceFolders = moduleDescriptorScanner.getSourceFolders(directory, name);

            modules.add(new Module(directory, name, artifactName, javaSourceFolders));
        }
        return FileVisitResult.CONTINUE;
    }
}
