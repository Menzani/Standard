package eu.menzani.swing;

import eu.menzani.struct.AppInfo;

import javax.swing.*;
import java.awt.*;

public class JMessageBox {
    private final Component parent;
    private final String title;

    public JMessageBox() {
        this(AppInfo.getName());
    }

    public JMessageBox(String title) {
        this(null, title);
    }

    public JMessageBox(Component parent, String title) {
        this.parent = parent;
        this.title = title;
    }

    public void showPlain(String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.PLAIN_MESSAGE);
    }
}
