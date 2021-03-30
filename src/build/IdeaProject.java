package eu.menzani.build;

import eu.menzani.concurrent.Lazy;
import eu.menzani.lang.Optional;
import eu.menzani.lang.UncaughtException;
import eu.menzani.misc.XmlParser;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class IdeaProject {
    private static final Lazy<IdeaProject> current = Lazy.of(() -> IdeaProject.fromFolder(SystemPaths.WORKING_DIRECTORY));

    public static IdeaProject current() {
        return current.get();
    }

    static final String IDEA_FOLDER_NAME = ".idea";

    private final Path directory;
    private final Path ideaFolder;
    private final String name;
    private final List<IdeaModule> modules;

    public static @Optional IdeaProject fromFolder(Path folder) {
        Path ideaFolder = folder.resolve(IDEA_FOLDER_NAME);
        if (Files.exists(ideaFolder)) {
            return new IdeaProject(folder, ideaFolder);
        }
        return null;
    }

    private IdeaProject(Path directory, Path ideaFolder) {
        this.directory = directory;
        this.ideaFolder = ideaFolder;
        name = directory.getFileName().toString();
        try {
            modules = readModules();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    private List<IdeaModule> readModules() throws IOException {
        IdeaProjectDescriptorScanner projectDescriptorScanner = new IdeaProjectDescriptorScanner();
        XmlParser.parse(ideaFolder.resolve("misc.xml"), projectDescriptorScanner);
        Path projectOutputDirectory = projectDescriptorScanner.getOutputDirectory();

        IdeaProjectScanner projectScanner = new IdeaProjectScanner(name);
        Files.walkFileTree(directory, projectScanner);
        return projectScanner.getModules(projectOutputDirectory);
    }

    public Path getDirectory() {
        return directory;
    }

    public String getName() {
        return name;
    }

    List<IdeaModule> getModules() {
        return modules;
    }

    public boolean isNotLibrary(String fileProjectName) {
        return name.equals(fileProjectName);
    }
}
