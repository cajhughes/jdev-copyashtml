package chughes.jdev.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.StringWriter;

/*
 * @author Chris Hughes
 *
 * This class provides utility methods for interaction with the system
 * clipboard.
 */
public class ClipboardUtil
{
    public static boolean hasClipboardAccess ()
    {
        boolean hasAccess = false;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
        {
            try
            {
                sm.checkSystemClipboardAccess();
                hasAccess = true;
            }
            catch (Exception e)
            {
                hasAccess = false;
            }
        }
        return hasAccess;
    }

    public static void setSystemClipboard (final StringWriter writer)
    {
        StringSelection selection = null;
        if (writer != null && writer.getBuffer() != null)
        {
            selection = new StringSelection(writer.getBuffer().toString());
            if (selection != null)
            {
                Clipboard system =
                    Toolkit.getDefaultToolkit().getSystemClipboard();
                system.setContents(selection, selection);
            }
        }
    }
}