package eu.menzani.swing;

import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.Percent;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class Swing {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void centerFrameHorizontally(JFrame frame, Percent y) {
        positionFrame(frame, Percent.FIFTY, y);
    }

    public static void centerFrameVertically(JFrame frame, Percent x) {
        positionFrame(frame, x, Percent.FIFTY);
    }

    public static void positionFrame(JFrame frame, Percent x, Percent y) {
        float widthDiff = screenSize.width - frame.getWidth();
        float heightDiff = screenSize.height - frame.getHeight();
        frame.setLocation(Math.round(widthDiff * x.getAsFractionAsFloat()), Math.round(heightDiff * y.getAsFractionAsFloat()));
    }

    public static void run(Runnable action) {
        SwingUtilities.invokeLater(action);
    }

    public static void schedule(int delay, Runnable action) {
        Timer timer = new Timer(delay, event -> action.run());
        timer.setRepeats(false);
        timer.start();
    }

    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new UncaughtException(e);
        }
    }

    public static void forEachComponent(Container container, Consumer<Component> action) {
        for (Component component : container.getComponents()) {
            action.accept(component);
            if (component instanceof Container) {
                forEachComponent((Container) component, action);
            }
        }
    }
}
