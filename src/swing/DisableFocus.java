package eu.menzani.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisableFocus {
    private final List<Component> excluded = new ArrayList<>();
    private final List<KeyListener> globalKeyListeners = new ArrayList<>();

    public DisableFocus() {
    }

    public DisableFocus(Component... excluded) {
        Collections.addAll(this.excluded, excluded);
    }

    public void exclude(Component component) {
        excluded.add(component);
    }

    public void addGlobalKeyListener(KeyListener keyListener) {
        globalKeyListeners.add(keyListener);
    }

    public void apply(JFrame frame) {
        apply(frame.getRootPane());
    }

    public void apply(Container container) {
        Swing.forEachComponent(container, component -> {
            for (KeyListener globalKeyListener : globalKeyListeners) {
                component.addKeyListener(globalKeyListener);
            }
            for (Component excludedComponent : excluded) {
                if (component == excludedComponent) {
                    return;
                }
            }
            component.setFocusable(false);
        });
    }
}
