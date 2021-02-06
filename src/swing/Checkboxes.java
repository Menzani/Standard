package eu.menzani.swing;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Checkboxes {
    public static void bindEnabled(Component component, Checkbox checkbox) {
        checkbox.addItemListener(event -> {
            component.setEnabled(event.getStateChange() == ItemEvent.SELECTED);
        });
    }

    public static void bindEnabledOr(Component component, Checkbox... checkboxes) {
        ItemListener listener = event -> {
            boolean enabled = false;
            for (Checkbox checkbox : checkboxes) {
                enabled |= checkbox.getState();
            }
            component.setEnabled(enabled);
        };
        for (Checkbox checkbox : checkboxes) {
            checkbox.addItemListener(listener);
        }
    }
}
