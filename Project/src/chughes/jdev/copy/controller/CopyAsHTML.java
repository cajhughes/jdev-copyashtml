package chughes.jdev.copy.controller;

import chughes.jdev.copy.model.Settings;
import chughes.jdev.copy.view.HTMLFormatter;
import chughes.jdev.copy.view.UserInterface;
import chughes.jdev.util.ClipboardUtil;
import chughes.jdev.util.EditorUtil;
import chughes.jdev.util.NodeUtil;
import java.io.StringWriter;
import oracle.ide.Ide;
import oracle.ide.controller.IdeAction;
import oracle.ide.Addin;
import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.log.LogManager;
import oracle.ide.model.TextNode;

/*
 * @author Chris Hughes
 */
public class CopyAsHTML implements Addin, Controller
{
    public static final String COPY_EXTENSION = "chughes.jdev.CopyAsHTML";
    public static final int COPY_COMMAND =
        Ide.findOrCreateCmdID(COPY_EXTENSION);

    /************************************************************************
     * Implement Addin interface
     ************************************************************************/
    public void initialize ()
    {
        /*
         * Initialize:
         *
         * (a) Our preferences, as they will not exist if this is the very
         *     first time we load the extension
         * (b) Our user interface elements within the IDE
         */
        Settings.initialize(COPY_EXTENSION);
        UserInterface.initialize(this, COPY_COMMAND);
    }

    /************************************************************************
     * Implement Controller interface
     ************************************************************************/
    public boolean handleEvent (final IdeAction action, final Context context)
    {
        boolean result = false;
        final int command = action.getCommandId();
        if (command == COPY_COMMAND)
        {
            executeCommand(context);
            result = true;
        }
        return result;
    }

    public boolean update (final IdeAction action, final Context context)
    {
        final int commandId = action.getCommandId();
        final boolean isEnabled = actionEnabled(context);
        if (commandId == COPY_COMMAND)
        {
            action.setEnabled(isEnabled);
        }
        return isEnabled;
    }

    /************************************************************************
     * Implement extension functionality
     ************************************************************************/

    /*
     * This method provides the implementation to determine whether the
     * copy command should be enabled currently.
     */
    protected boolean actionEnabled (final Context context)
    {
        return (
            ClipboardUtil.hasClipboardAccess() &&
            NodeUtil.isTextNode(context) &&
            EditorUtil.hasActiveSelection(context)
        );
    }

    /*
     * This method is the main entry-point for the extension functionality.
     */
    protected void executeCommand (final Context context)
    {
        if (context != null && actionEnabled(context))
        {
            Ide.getWaitCursor().show();
            final TextNode node = (TextNode)context.getElement();
            final StringWriter writer = new StringWriter();
            try
            {
                final HTMLFormatter formatter =
                    new HTMLFormatter(node.getShortLabel(),
                                      EditorUtil.getSelectedText(context));
                formatter.format(
                    writer,
                    Settings.getCurrent(COPY_EXTENSION).getCopyFormat());
                ClipboardUtil.setSystemClipboard(writer);
            }
            catch (Exception e)
            {
                LogManager.getLogManager().getMsgPage().log(e);
            }
            finally
            {
                Ide.getWaitCursor().hide();
            }
        }
    }
}