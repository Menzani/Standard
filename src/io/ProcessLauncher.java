package eu.menzani.io;

import eu.menzani.lang.Nonblocking;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.Patterns;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessLauncher {
    public final ProcessBuilder builder;

    private final List<String> command = new ArrayList<>();
    private Process process;
    private Writer inputWriter;

    public ProcessLauncher(String program) {
        command.add(program);
        builder = new ProcessBuilder(Collections.unmodifiableList(command));
    }

    public void setWorkingDirectory(Path workingDirectory) {
        builder.directory(workingDirectory.toFile());
    }

    public void inheritInput() {
        builder.redirectInput(ProcessBuilder.Redirect.INHERIT);
    }

    public void inheritOutput() {
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
    }

    public void inheritError() {
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
    }

    public void addArgument(String argument) {
        command.add(argument);
    }

    public void addArguments(String... arguments) {
        Collections.addAll(command, arguments);
    }

    public ProcessLauncher start() {
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
        return this;
    }

    public Process getProcess() {
        return process;
    }

    public void beginWrite(Charset charset) {
        inputWriter = new OutputStreamWriter(process.getOutputStream(), charset);
    }

    public void writeLine(String line) {
        try {
            inputWriter.write(line);
            inputWriter.write('\n');
            inputWriter.flush();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public void endWrite() {
        try {
            inputWriter.close();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public Iterable<String> outputIterator(Charset charset) {
        return new InputStreamIterator(process.getInputStream(), charset);
    }

    public Iterable<String> errorIterator(Charset charset) {
        return new InputStreamIterator(process.getErrorStream(), charset);
    }

    public String readOutput() {
        return read(process.getInputStream());
    }

    public String readError() {
        return read(process.getErrorStream());
    }

    private String read(InputStream stream) {
        await();
        return IOStreams.toString(stream);
    }

    public String[] readOutputLines() {
        return Patterns.SPLIT_BY_NEWLINE.split(readOutput());
    }

    public String[] readErrorLines() {
        return Patterns.SPLIT_BY_NEWLINE.split(readError());
    }

    public void await() {
        Nonblocking.waitFor(process);
    }
}
