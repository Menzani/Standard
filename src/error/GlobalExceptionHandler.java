package eu.menzani.error;

import eu.menzani.lang.Optional;
import eu.menzani.struct.Patterns;
import eu.menzani.swing.Swing;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    public static void register() {
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

    private static final GlobalExceptionHandler instance = registerInstance();

    private static GlobalExceptionHandler registerInstance() {
        try {
            GlobalExceptionHandler instance = new GlobalExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(instance);
            return instance;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private final List<Action> actions = new CopyOnWriteArrayList<>();
    private final List<SwingAction> swingActions = new CopyOnWriteArrayList<>();

    private GlobalExceptionHandler() {
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        try {
            throwable = process(throwable);
            if (throwable == null) return;

            String stackTrace = "Exception in thread \"" + thread.getName() + "\" " +
                    GlobalStackTraceFilter.getInstance().printFilteredStackTraceToString(throwable);

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

    public static @Optional Throwable process(Throwable throwable) {
        if (throwable instanceof ThreadDeath) {
            return null;
        }
        if (throwable instanceof ExceptionInInitializerError) {
            throwable = throwable.getCause();
        }

        if (throwable instanceof HandledThrowable) {
            HandledThrowable handled = (HandledThrowable) throwable;
            handled.run();
            if (handled.shouldBeIgnored()) {
                return null;
            }
        }

        return throwable;
    }
}
