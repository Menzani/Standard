package eu.menzani.swing;

import javax.swing.*;
import java.awt.event.MouseListener;

public class JCheckBoxNonEditable extends JCheckBox {
    private static final MouseListener builtinMouseListener = new JCheckBox().getMouseListeners()[0];

    private boolean editable;

    public JCheckBoxNonEditable() {
    }

    public JCheckBoxNonEditable(Icon icon) {
        super(icon);
    }

    public JCheckBoxNonEditable(Icon icon, boolean selected) {
        super(icon, selected);
    }

    public JCheckBoxNonEditable(String text) {
        super(text);
    }

    public JCheckBoxNonEditable(Action a) {
        super(a);
    }

    public JCheckBoxNonEditable(String text, boolean selected) {
        super(text, selected);
    }

    public JCheckBoxNonEditable(String text, Icon icon) {
        super(text, icon);
    }

    public JCheckBoxNonEditable(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        if (editable != this.editable) {
            this.editable = editable;
            if (editable) {
                addMouseListener(builtinMouseListener);
            } else {
                removeMouseListener(builtinMouseListener);
            }
        }
    }
}
