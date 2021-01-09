package eu.menzani.system;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class Clipboard {
    private static final java.awt.datatransfer.Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public static void setText(String text) {
        systemClipboard.setContents(new StringSelection(text), null);
    }
}
