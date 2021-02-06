package eu.menzani.swing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface SimpleDocumentListener extends DocumentListener {
    void update(DocumentEvent event);

    @Override
    default void insertUpdate(DocumentEvent event) {
        update(event);
    }

    @Override
    default void removeUpdate(DocumentEvent event) {
        update(event);
    }

    @Override
    default void changedUpdate(DocumentEvent event) {
        update(event);
    }
}
