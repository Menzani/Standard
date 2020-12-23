package eu.menzani.swing;

import eu.menzani.lang.UncaughtException;

import javax.swing.*;
import java.awt.*;

public class Swing {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void centerFrame(JFrame frame, int y) {
        if (y < 0) {
            y += screenSize.getHeight() - frame.getHeight();
        }
        int screenWidth = (int) screenSize.getWidth();
        frame.setLocation((screenWidth - frame.getWidth()) / 2, y);
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
}
