package eu.menzani.error;

import eu.menzani.struct.Patterns;
import eu.menzani.swing.Swing;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    public static void addIgnored(Class<? extends Throwable> ignored) {
        try {
            instance.ignored.add(ignored);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void addAction(Action action) {
        try {
            instance.actions.add(action);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void addErrorReport() {
        try {
            addAction(new ErrorReport());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void addErrorReport(FinalAction finalAction, String header) {
        try {
            addAction(new ErrorReport(header, finalAction));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void addSwingAction(SwingAction swingAction) {
        try {
            instance.swingActions.add(swingAction);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static final GlobalExceptionHandler instance = createInstance();

    private static GlobalExceptionHandler createInstance() {
        try {
            GlobalExceptionHandler instance = new GlobalExceptionHandler();
            instance.register();
            return instance;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private final List<Class<? extends Throwable>> ignored = new CopyOnWriteArrayList<>();
    private final List<Action> actions = new CopyOnWriteArrayList<>();
    private final List<SwingAction> swingActions = new CopyOnWriteArrayList<>();

    private GlobalExceptionHandler() {
    }

    private void register() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            if (throwable instanceof ThreadDeath) {
                return;
            }
            if (throwable instanceof ExceptionInInitializerError) {
                throwable = throwable.getCause();
            }

            if (ignored.contains(throwable.getClass())) {
                return;
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
