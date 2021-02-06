package eu.menzani.swing;

import eu.menzani.lang.Optional;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class JTextFieldWithPlaceholder extends JTextField {
    private String placeholder;

    public JTextFieldWithPlaceholder() {
    }

    public JTextFieldWithPlaceholder(String text) {
        super(text);
    }

    public JTextFieldWithPlaceholder(int columns) {
        super(columns);
    }

    public JTextFieldWithPlaceholder(String text, int columns) {
        super(text, columns);
    }

    public JTextFieldWithPlaceholder(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public @Optional String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(@Optional String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (placeholder != null && isEnabled() && getText().isEmpty()) {
            Graphics2D pen = (Graphics2D) graphics;
            pen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            pen.setColor(getDisabledTextColor());
            Insets insets = getInsets();
            pen.drawString(placeholder, insets.left, graphics.getFontMetrics().getMaxAscent() + insets.top);
        }
    }
}
