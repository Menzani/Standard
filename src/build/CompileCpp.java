package eu.menzani.build;

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
    public static void run() {
        try {
            main();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        main();
    }

    private static void main() throws IOException {
        if (checkRequirements()) return;

        CompileCpp compileCpp = new CompileCpp();
        try {
            compileCpp.compile();
        } catch (CppCompilerException e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean checkRequirements() {
        if (!Platform.current().is64Bit()) {
            System.err.println("Platform must be 64-bit.");
            return true;
        }
        return false;
    }

    private final Project project;

    private CompileCpp() throws IOException {
        project = new Project();
    }

    private void compile() throws IOException, CppCompilerException {
        for (Module module : project.getModules()) {
            Path nativeSourceFolder = module.getNativeSourceFolder();
            if (Files.exists(nativeSourceFolder)) {
                Path outputFileWithoutExtension = module.getProductionOutputDirectory().resolve(NativeLibrary.FOLDER_IN_ARTIFACT).resolve(module.getName());
                CppCompiler cppCompiler = new CppCompiler(nativeSourceFolder, module.getNativeOutputDirectory(), outputFileWithoutExtension);
                cppCompiler.createOutputFolders();
                cppCompiler.compile();
            }
        }
    }
}
