package eu.menzani.swing;

import eu.menzani.misc.Application;

import javax.swing.*;
import java.awt.*;

public class MessageBox {
    private final Component parent;
    private final String title;

    public MessageBox() {
        this(Application.getName());
    }

    public MessageBox(String title) {
        this(null, title);
    }

    public MessageBox(Component parent, String title) {
        this.parent = parent;
        this.title = title;
    }

    public void showPlain(String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.PLAIN_MESSAGE);
    }
}
