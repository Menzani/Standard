package eu.menzani.error;

import eu.menzani.misc.Patterns;
import eu.menzani.swing.Swing;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    public static void addAction(Action action) {
        instance.actions.add(action);
    }

    public static void addErrorReport() {
        addAction(new ErrorReport());
    }

    public static void addSwingAction(SwingAction swingAction) {
        instance.swingActions.add(swingAction);
    }

    private static final GlobalExceptionHandler instance = new GlobalExceptionHandler();

    static {
        try {
            instance.register();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private final List<Action> actions = new CopyOnWriteArrayList<>();
    private final List<SwingAction> swingActions = new CopyOnWriteArrayList<>();

    private GlobalExceptionHandler() {
    }

    private void register() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (throwable instanceof ThreadDeath) {
            return;
        }
        try {
            if (throwable instanceof ExceptionInInitializerError) {
                throwable = throwable.getCause();
            }

            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            String stackTrace = "Exception in thread \"" + thread.getName() + "\" " + writer.toString();

            if (!swingActions.isEmpty()) {
                String stackTraceForLabel = "<html>" + stackTrace.replace("\n", "<br>").replace("\t", "&emsp;") + "</html>";
                Swing.run(() -> {
                    try {
                        for (SwingAction swingAction : swingActions) {
                            swingAction.run(stackTrace, stackTraceForLabel);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
            }
            String stackTraceForFile = Patterns.normalizeLineSeparators(stackTrace);
            FinalAction finalAction = NoFinalAction.INSTANCE;
            for (Action action : actions) {
                FinalAction desiredFinalAction = action.run(stackTrace, stackTraceForFile);
                if (desiredFinalAction.getSeverity() > finalAction.getSeverity()) {
                    finalAction = desiredFinalAction;
                }
            }

            System.err.print(stackTrace);
            finalAction.run();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
