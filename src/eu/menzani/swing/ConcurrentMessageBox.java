package eu.menzani.swing;

import java.awt.*;

public class ConcurrentMessageBox extends MessageBox {
    public ConcurrentMessageBox() {
        super();
    }

    public ConcurrentMessageBox(String title) {
        super(title);
    }

    public ConcurrentMessageBox(Component parent, String title) {
        super(parent, title);
    }

    @Override
    public void showPlain(String message) {
        Swing.run(() -> super.showPlain(message));
    }
}
