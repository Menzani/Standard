package eu.menzani.build;

import eu.menzani.io.PrintToConsoleException;
import eu.menzani.io.SimpleDirectoryStreamFilter;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.DigestFile;
import eu.menzani.struct.FileExtension;
import eu.menzani.system.Platform;
import eu.menzani.system.PlatformFamilyDependant;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

class CppCompiler {
    private static final SimpleDirectoryStreamFilter.Builder filterBuilder = SimpleDirectoryStreamFilter.builder()
            .excludeDirectories()
            .orMustEndWith("_share")
            .orHasExtension(FileExtension.CPP)
            .orHasExtension(FileExtension.C);

    private final Path sourcesFolder;
    private final Path outputFolder;
    private final Path outputFileWithoutExtension;
    private final DigestFile sourcesDigest;
    private final boolean shouldRebuild;

    CppCompiler(Path sourcesFolder, Path outputFolder, Path outputFileWithoutExtension, String moduleName, boolean shouldRebuild) {
        this.sourcesFolder = sourcesFolder;
        this.outputFolder = outputFolder;
        this.outputFileWithoutExtension = outputFileWithoutExtension;
        sourcesDigest = new DigestFile(SystemPaths.TEMP_FOLDER, "native-build_" + moduleName);
        this.shouldRebuild = shouldRebuild;
    }

    void compile() {
        new Compile();
    }

    private class Compile extends PlatformFamilyDependant {
        @Override
        protected void onWindows() {
            try {
                Set<Path> sources = findSources("windows");

                if (shouldCompile(sources)) {
                    createOutputFolders();

                    WindowsCppCompiler compiler = new WindowsCppCompiler(sources, outputFolder, outputFileWithoutExtension);
                    compiler.invokeCl();
                }
            } catch (IOException e) {
                throw new UncaughtException(e);
            }
        }
    }

    private Set<Path> findSources(String fileNameSuffix) throws IOException {
        SimpleDirectoryStreamFilter filter = filterBuilder.orMustEndWith('_' + fileNameSuffix).build();
        SortedSet<Path> sources = new TreeSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcesFolder, filter)) {
            for (Path path : stream) {
                sources.add(path);
            }
        }
        if (sources.isEmpty()) {
            throw new PrintToConsoleException("No sources found.");
        }
        return Collections.unmodifiableSet(sources);
    }

    private boolean shouldCompile(Collection<Path> sources) throws IOException {
        sourcesDigest.addFiles(sources);
        if (shouldRebuild) {
            sourcesDigest.save();
            return true;
        }
        return sourcesDigest.wereChanged();
    }

    private void createOutputFolders() throws IOException {
        Files.createDirectories(outputFolder);
        Files.createDirectories(outputFileWithoutExtension.getParent());
    }

    static String outputFileSuffixFor(Platform.Architecture architecture) {
        switch (architecture) {
            case BIT_32:
                return "_32";
            case BIT_64:
                return "_64";
            default:
                throw new AssertionError();
        }
    }
}
