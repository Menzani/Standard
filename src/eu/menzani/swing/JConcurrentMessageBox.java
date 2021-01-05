package eu.menzani.swing;

import java.awt.*;

public class JConcurrentMessageBox extends JMessageBox {
    public JConcurrentMessageBox() {
        super();
    }

    public JConcurrentMessageBox(String title) {
        super(title);
    }

    public JConcurrentMessageBox(Component parent, String title) {
        super(parent, title);
    }

    @Override
    public void showPlain(String message) {
        Swing.run(() -> super.showPlain(message));
    }
}
