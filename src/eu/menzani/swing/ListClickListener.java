package eu.menzani.swing;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface ListClickListener<T> extends MouseListener {
    void listElementClicked(T element, MouseEvent event);

    @Override
    default void mouseClicked(MouseEvent event) {
        @SuppressWarnings("unchecked") JList<T> list = (JList<T>) event.getSource();
        int index = list.locationToIndex(event.getPoint());
        if (index != -1) {
            T element = list.getModel().getElementAt(index);
            listElementClicked(element, event);
        }
    }

    @Override
    default void mousePressed(MouseEvent event) {
    }

    @Override
    default void mouseReleased(MouseEvent event) {
    }

    @Override
    default void mouseEntered(MouseEvent event) {
    }

    @Override
    default void mouseExited(MouseEvent event) {
    }
}
