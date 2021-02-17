package eu.menzani.build;

import eu.menzani.io.PrintToConsoleException;
import eu.menzani.lang.UncaughtException;
import eu.menzani.misc.NativeLibrary;
import eu.menzani.system.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <b>CentOS 7</b>
 *
 * <pre>{@code
 * yum install gcc glibc-devel.i686 libgcc.i686
 * yum install java-11-openjdk-devel
 * }</pre>
 *
 * <b>Windows 10</b>
 * <p>
 * Install Visual Studio 2019 components:
 *
 * <pre>{@code
 * MSVC - VS C++ x64/x86 build tools
 * Windows 10 SDK
 * }</pre>
 */
public class CompileCpp {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        checkRequirements();

        try {
            CompileCpp compileCpp = new CompileCpp();
            compileCpp.compile();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    private static void checkRequirements() {
        if (!Platform.current().is64Bit()) {
            throw new PrintToConsoleException("Platform must be 64-bit.");
        }
    }

    private final Project project;

    private CompileCpp() throws IOException {
        project = new Project();
    }

    private void compile() throws IOException {
        for (Module module : project.getModules()) {
            Path nativeSourceFolder = module.getNativeSourceFolder();
            if (Files.exists(nativeSourceFolder)) {
                Path outputFileWithoutExtension = module.getProductionOutputDirectory()
                        .resolve(NativeLibrary.FOLDER_IN_ARTIFACT).resolve(module.getName());
                CppCompiler cppCompiler = new CppCompiler(
                        nativeSourceFolder, module.getNativeOutputDirectory(), outputFileWithoutExtension);
                cppCompiler.createOutputFolders();
                cppCompiler.compile();
            }
        }
    }
}
