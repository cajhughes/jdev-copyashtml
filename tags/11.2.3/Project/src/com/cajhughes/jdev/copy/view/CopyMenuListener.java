package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.CopyCommand;
import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

/**
 * This class implements the ContextMenuListener interface, and exists to add
 * the CopyAsHtml action to the context menu within the CodeEditor.
 *
 * @author Chris Hughes
 */
public final class CopyMenuListener implements ContextMenuListener {
    @Override
    public boolean handleDefaultAction(Context context) {
        return false;
    }

    @Override
    public void menuWillShow(final ContextMenu contextMenu) {
        contextMenu.add(contextMenu.createMenuItem(IdeAction.find(CopyCommand.actionId())));
    }

    @Override
    public void menuWillHide(final ContextMenu contextMenu) {
    }
}
