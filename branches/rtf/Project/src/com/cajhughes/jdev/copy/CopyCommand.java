package com.cajhughes.jdev.copy;

import com.cajhughes.jdev.copy.model.CopySettings;
import com.cajhughes.jdev.copy.view.Formatter;
import com.cajhughes.jdev.copy.view.resource.CopyResourceUtil;
import com.cajhughes.jdev.util.ClipboardUtil;
import com.cajhughes.jdev.util.EditorUtil;
import java.io.StringWriter;
import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.log.LogManager;
import oracle.ide.model.TextNode;

/**
 * This class extends the Command class, and provides the core implementation
 * of the CopyAsHtml action.
 *
 * @author Chris Hughes
 */
public class CopyCommand extends Command {
    public static final String EXTENSION_ID = "com.cajhughes.jdev.CopyAsHtml";
    private static final String EXTENSION_NAME = CopyResourceUtil.getString("EXTENSION_NAME");
    private static final String SQL = ".sql";

    public CopyCommand() {
        super(actionId(), NO_CHANGE);
    }

    public static int actionId() {
        Integer cmdId = Ide.findOrCreateCmdID(EXTENSION_ID);
        if (cmdId == null) {
            throw new IllegalStateException("Action, " + EXTENSION_ID + ", not found.");
        }
        else {
            return cmdId.intValue();
        }
    }

    @Override
    public String getName() {
        return EXTENSION_NAME;
    }

    @Override
    public int getType() {
        return NO_CHANGE;
    }

    @Override
    public int doit() throws Exception {
        Context context = getContext();
        if (context != null) {
            Ide.getWaitCursor().show();
            final TextNode node = (TextNode)context.getNode();
            final StringWriter writer = new StringWriter();
            try {
                String selectedText = EditorUtil.getSelectedText(context);
                final Formatter formatter = new Formatter(getFilename(node), selectedText);
                formatter.format(writer, CopySettings.getCurrent());
                ClipboardUtil.setContents(writer, CopySettings.getCurrent().getCopyFormat(), selectedText);
            }
            catch (Exception e) {
                LogManager.getLogManager().getMsgPage().log(e);
            }
            finally {
                Ide.getWaitCursor().hide();
            }
        }
        return OK;
    }

    private String getFilename(final TextNode node) {
        String filename = node.getShortLabel();
        try {
            if (Class.forName("oracle.ide.db.model.DBObjectNode").isInstance(node) ||
                Class.forName("oracle.ide.db.model.SqlNode").isInstance(node)) {
                if (!filename.endsWith(SQL)) {
                    filename = filename + SQL;
                }
            }
        }
        catch (ClassNotFoundException cnfe) {
            filename = node.getShortLabel();
        }
        return filename;
    }
}
