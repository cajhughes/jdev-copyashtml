package com.cajhughes.jdev.util;

import com.cajhughes.jdev.copy.model.CopyPreferences;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 * This class provides static helper methods for accessing and interacting
 * with the system clipboard.
 *
 * @author Chris Hughes
 */
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

    public static void setContents(final StringWriter writer, final int format) {
        if (writer != null && writer.getBuffer() != null) {
            Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (format != CopyPreferences.RTF) {
                StringSelection selection = new StringSelection(writer.getBuffer().toString());
                system.setContents(selection, selection);
            }
            else {
                RtfTransferable transferable =
                    new RtfTransferable(new ByteArrayInputStream(writer.getBuffer().toString().getBytes()));
                system.setContents(transferable, null);
            }
        }
    }
}
