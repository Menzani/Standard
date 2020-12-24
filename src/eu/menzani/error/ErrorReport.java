package eu.menzani.error;

import eu.menzani.misc.Application;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;

class ErrorReport implements Action {
    private static final Desktop desktop = Desktop.getDesktop();

    private final String header;

    ErrorReport() {
        String ln = System.lineSeparator();
        header = "An error occurred in " + Application.getName() + ln + ln +
                "The application was terminated to prevent data corruption." + ln +
                "Some data may have been lost." + ln + ln +
                "Error details:" + ln +
                "=========================================================================================" + ln;
    }

    @Override
    public FinalAction run(String stackTrace, String stackTraceForFile) throws Throwable {
        Path reportFile = Files.createTempFile("An error occurred   ", ".txt");
        Files.writeString(reportFile, header + stackTraceForFile);
        desktop.open(reportFile.toFile());
        return HaltFinalAction.INSTANCE;
    }
}
