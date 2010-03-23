package com.cajhughes.jdev.util;

import com.cajhughes.jdev.copy.model.CopyPreferences;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import oracle.ide.log.LogManager;

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

    public static void setContents(final StringWriter writer, final int format, final String text) {
        if (writer != null && writer.getBuffer() != null) {
            Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringBuffer buffer = writer.getBuffer();
            CopyTransferable transferable = null;
            try {
                if (CopyPreferences.RTF == format) {
                    transferable = new CopyTransferable(new ByteArrayInputStream(buffer.toString().getBytes()),
                                                        CopyTransferable.RTF_FLAVOR);
                    transferable.addDataFlavor(text, DataFlavor.stringFlavor);
                }
                else {
                    transferable = new CopyTransferable(buffer.toString(), DataFlavor.stringFlavor);
                }
            }
            catch (UnsupportedFlavorException ufe) {
                LogManager.getLogManager().getMsgPage().log(ufe.getMessage());
                transferable = null;
            }
            if (transferable != null) {
                system.setContents(transferable, null);
            }
        }
    }
}
