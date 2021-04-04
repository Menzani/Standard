package eu.menzani.error;

import eu.menzani.struct.AppInfo;
import eu.menzani.struct.Strings;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;

class ErrorReport implements Action {
    private static final Desktop desktop = Desktop.getDesktop();

    private final String header;
    private final FinalAction finalAction;

    ErrorReport() {
        this(createDefaultHeader(), HaltFinalAction.INSTANCE);
    }

    private static String createDefaultHeader() {
        final String ln = Strings.LN;
        return "An error occurred in " + AppInfo.getName() + ln + ln +
                "The application was terminated to prevent data corruption." + ln +
                "Some data may have been lost." + ln + ln +
                "Error details:" + ln +
                "=========================================================================================" + ln;
    }

    ErrorReport(String header, FinalAction finalAction) {
        this.header = header;
        this.finalAction = finalAction;
    }

    @Override
    public FinalAction run(String stackTrace, String stackTraceForFile) throws Throwable {
        Path reportFile = Files.createTempFile("An error occurred   ", ".txt");
        Files.writeString(reportFile, header + stackTraceForFile);
        desktop.open(reportFile.toFile());
        return finalAction;
    }
}
