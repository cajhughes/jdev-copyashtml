package com.cajhughes.jdev.copy.controller;

import com.cajhughes.jdev.util.ClipboardUtil;
import com.cajhughes.jdev.util.EditorUtil;
import com.cajhughes.jdev.util.NodeUtil;
import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;

public class CopyController implements Controller {
    public boolean handleEvent(final IdeAction action, final Context context) {
        return false;
    }

    public boolean update(final IdeAction action, final Context context) {
        action.setEnabled(
            ClipboardUtil.canBeAccessed() && NodeUtil.isTextNode(context) && EditorUtil.hasActiveSelection(context));
        return true;
    }
}
