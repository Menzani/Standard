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

class IdeaProjectScanner extends SimpleFileVisitor<Path> {
    private final String projectName;
    private final List<IdeaModule> modules = new ArrayList<>();

    IdeaProjectScanner(String projectName) {
        this.projectName = projectName;
    }

    List<IdeaModule> getModules(Path projectOutputDirectory) {
        for (IdeaModule module : modules) {
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
            case IdeaModule.NATIVE_FOLDER_NAME:
            case "lib":
            case IdeaProject.IDEA_FOLDER_NAME:
            case ".git":
                return FileVisitResult.SKIP_SUBTREE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".iml")) {
            Path directory = file.getParent();
            Path nameAsFolder = directory.getFileName();
            String name = nameAsFolder.toString();

            String artifactName;
            if (name.equals(projectName)) {
                artifactName = name;
            } else {
                artifactName = projectName + '-' + name;
            }

            IdeaModuleDescriptorScanner moduleDescriptorScanner = new IdeaModuleDescriptorScanner();
            XmlParser.parse(file, moduleDescriptorScanner);
            List<JarSource> javaSourceFolders = moduleDescriptorScanner.getSourceFolders(directory, name);

            modules.add(new IdeaModule(directory, name, nameAsFolder, artifactName, javaSourceFolders));
        }
        return FileVisitResult.CONTINUE;
    }
}
