package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.CopyCommand;
import oracle.ide.Context;
import oracle.ide.controller.ContextMenu;
import oracle.ide.controller.ContextMenuListener;
import oracle.ide.controller.IdeAction;

public final class CopyMenuListener implements ContextMenuListener {
    public void menuWillShow(final ContextMenu contextMenu) {
        contextMenu.add(contextMenu.createMenuItem(IdeAction.find(CopyCommand.actionId())));
    }

    public void menuWillHide(final ContextMenu contextMenu) {
    }

    public boolean handleDefaultAction(Context context) {
        return false;
    }
}
