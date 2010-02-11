package com.cajhughes.jdev.copy;

import com.cajhughes.jdev.copy.model.CopySettings;
import com.cajhughes.jdev.copy.view.HtmlFormatter;
import com.cajhughes.jdev.copy.view.resource.CopyResourceUtil;
import com.cajhughes.jdev.util.ClipboardUtil;
import com.cajhughes.jdev.util.EditorUtil;
import java.io.StringWriter;
import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.log.LogManager;
import oracle.ide.model.Node;
import oracle.ide.model.TextNode;

public class CopyCommand extends Command {
    public static final String EXTENSION_ID = "com.cajhughes.jdev.CopyAsHtml";
    private static final String EXTENSION_NAME = CopyResourceUtil.getString("EXTENSION_NAME");

    public CopyCommand() {
        super(actionId(), NO_CHANGE);
    }

    public static int actionId() {
        Integer cmdId = Ide.findOrCreateCmdID(EXTENSION_ID);
        if (cmdId == null) {
            throw new IllegalStateException("Action " + EXTENSION_ID + " not found.");
        }
        else {
            return cmdId.intValue();
        }
    }

    public Node[] getAffectedNodes() {
        return null;
    }

    public String getName() {
        return EXTENSION_NAME;
    }

    public int getType() {
        return NO_CHANGE;
    }

    public int doit() throws Exception {
        Context context = getContext();
        if (context != null) {
            Ide.getWaitCursor().show();
            final TextNode node = (TextNode)context.getNode();
            final StringWriter writer = new StringWriter();
            try {
                final HtmlFormatter formatter =
                    new HtmlFormatter(node.getShortLabel(), EditorUtil.getSelectedText(context));
                formatter.format(writer, CopySettings.getCurrent(EXTENSION_ID).getCopyFormat());
                ClipboardUtil.setContents(writer);
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
}
