package eu.menzani.misc;

import eu.menzani.swing.Swing;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    public static void addAction(Action action) {
        instance.actions.add(action);
    }

    public static void addSwingAction(SwingAction swingAction) {
        instance.swingActions.add(swingAction);
    }

    private static final GlobalExceptionHandler instance = new GlobalExceptionHandler();

    static {
        instance.register();
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
                    for (SwingAction swingAction : swingActions) {
                        swingAction.run(stackTrace, stackTraceForLabel);
                    }
                });
            }
            for (Action action : actions) {
                action.run(stackTrace);
            }

            System.err.println(stackTrace);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public interface Action {
        void run(String stackTrace);
    }

    public interface SwingAction {
        void run(String stackTrace, String stackTraceForLabel);
    }
}
