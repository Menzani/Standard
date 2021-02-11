package eu.menzani.build;

import eu.menzani.io.PrintToConsoleException;
import eu.menzani.lang.Nonblocking;
import eu.menzani.system.Platform;
import eu.menzani.system.SystemProperty;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

class WindowsCppCompiler {
    private static final Path vcvarsallPath = findVcvarsallPath();

    private static Path findVcvarsallPath() {
        final Path fromBuildTools = Path.of("C:", "Program Files (x86)", "Microsoft Visual Studio", "2019", "BuildTools", "VC", "Auxiliary", "Build", "vcvarsall.bat");
        if (Files.exists(fromBuildTools)) {
            return fromBuildTools;
        }
        final Path fromCommunity = Path.of("C:", "Program Files (x86)", "Microsoft Visual Studio", "2019", "Community", "VC", "Auxiliary", "Build", "vcvarsall.bat");
        if (Files.exists(fromCommunity)) {
            return fromCommunity;
        }
        final Path fromProfessional = Path.of("C:", "Program Files (x86)", "Microsoft Visual Studio", "2019", "Professional", "VC", "Auxiliary", "Build", "vcvarsall.bat");
        if (Files.exists(fromProfessional)) {
            return fromProfessional;
        }
        throw new PrintToConsoleException("Could not find vcvarsall.bat file.");
    }

    private final Set<Path> sources;
    private final Path workingDirectory;
    private final Path outputFileWithoutExtension;

    WindowsCppCompiler(Set<Path> sources, Path workingDirectory, Path outputFileWithoutExtension) {
        this.sources = sources;
        this.workingDirectory = workingDirectory;
        this.outputFileWithoutExtension = outputFileWithoutExtension;
    }

    void invokeCl() throws IOException {
        invokeCl(Platform.Architecture.BIT_32);
        invokeCl(Platform.Architecture.BIT_64);
    }

    private void invokeCl(Platform.Architecture architecture) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(List.of("cmd.exe"));
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        builder.directory(workingDirectory.toFile());
        Process process = builder.start();

        String outputFile = outputFileWithoutExtension.toAbsolutePath().toString() + CppCompiler.outputFileSuffixFor(architecture);
        try (OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream())) {
            writer.append("call \"");
            writer.append(vcvarsallPath.toString());
            writer.append("\" ");
            writer.append(architecture == Platform.Architecture.BIT_32 ? "x86" : "x64");
            writer.append('\n');
            writer.flush();

            writer.append("cl.exe /I \"");
            Path includeFolder = SystemProperty.JAVA_HOME.resolve("include");
            writer.append(includeFolder.toString());
            writer.append("\" /I \"");
            writer.append(includeFolder.resolve("win32").toString());
            writer.append("\" /Fe\"");
            writer.append(outputFile);
            writer.append("\" /LD");
            for (Path source : sources) {
                writer.append(' ');
                writer.append(source.toAbsolutePath().toString());
            }
            writer.append('\n');
        }
        Nonblocking.waitFor(process);

        removeUnneededOutput(outputFile);
    }

    private static void removeUnneededOutput(String outputFile) throws IOException {
        Path expFile = Path.of(outputFile + ".exp");
        Path libFile = Path.of(outputFile + ".lib");
        Files.delete(expFile);
        Files.delete(libFile);
    }
}
