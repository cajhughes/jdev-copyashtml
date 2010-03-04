package com.cajhughes.jdev.copy.controller;

import com.cajhughes.jdev.copy.CopyCommand;
import com.cajhughes.jdev.util.EditorUtil;
import oracle.ide.Context;
import oracle.ide.ceditor.CodeEditor;
import oracle.ide.ceditor.CursorListener;
import oracle.ide.controller.IdeAction;

/**
 * This class implements the CursorListener interface, and exists to update
 * the enabled status of the CopyAsHtml action each time the cursor moves
 * within the Editor.
 *
 * @author Chris Hughes
 */
public final class CopyCursorListener implements CursorListener {
    private static final IdeAction copyAction = IdeAction.find(CopyCommand.actionId());

    @Override
    public void cursorUpdate(CodeEditor codeEditor) {
        if (codeEditor != null) {
            Context context = codeEditor.getContext();
            copyAction.setEnabled(EditorUtil.hasActiveSelection(context));
        }
    }
}
