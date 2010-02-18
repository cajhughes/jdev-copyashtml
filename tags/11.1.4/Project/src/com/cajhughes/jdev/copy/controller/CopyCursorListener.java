package com.cajhughes.jdev.copy.controller;

import com.cajhughes.jdev.copy.CopyCommand;
import com.cajhughes.jdev.util.EditorUtil;
import oracle.ide.Context;
import oracle.ide.ceditor.CodeEditor;
import oracle.ide.ceditor.CursorListener;
import oracle.ide.controller.IdeAction;

public final class CopyCursorListener implements CursorListener {
    private static final IdeAction copyAction = IdeAction.find(CopyCommand.actionId());

    public void cursorUpdate(CodeEditor codeEditor) {
        if (codeEditor != null) {
            Context context = codeEditor.getContext();
            copyAction.setEnabled(EditorUtil.hasActiveSelection(context));
        }
    }
}
