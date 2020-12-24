package eu.menzani.swing;

import javax.swing.text.JTextComponent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SelectAllListener extends FocusAdapter {
    public static final SelectAllListener INSTANCE = new SelectAllListener();

    @Override
    public void focusGained(FocusEvent event) {
        JTextComponent textComponent = (JTextComponent) event.getSource();
        textComponent.selectAll();
    }
}
