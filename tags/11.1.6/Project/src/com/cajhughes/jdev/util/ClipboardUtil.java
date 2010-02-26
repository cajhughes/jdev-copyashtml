package com.cajhughes.jdev.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import java.io.StringWriter;

public final class ClipboardUtil {
    public static boolean canBeAccessed() {
        boolean access = false;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            try {
                sm.checkSystemClipboardAccess();
                access = true;
            }
            catch (SecurityException se) {
                access = false;
            }
        }
        return access;
    }

    public static void setContents(final StringWriter writer) {
        StringSelection selection = null;
        if (writer != null && writer.getBuffer() != null) {
            selection = new StringSelection(writer.getBuffer().toString());
            if (selection != null) {
                Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
                system.setContents(selection, selection);
            }
        }
    }
}
