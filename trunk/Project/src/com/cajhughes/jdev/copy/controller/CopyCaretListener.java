package com.cajhughes.jdev.copy.controller;

import com.cajhughes.jdev.copy.CopyCommand;
import com.cajhughes.jdev.util.EditorUtil;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import oracle.ide.controller.IdeAction;
import oracle.ide.editor.Editor;

public class CopyCaretListener implements CaretListener {
    private static final IdeAction copyAction = IdeAction.find(CopyCommand.actionId());
    private Editor editor = null;
    
    public CopyCaretListener(final Editor editor) {
        this.editor = editor;
    }

    public void caretUpdate(final CaretEvent event) {
        if (editor != null) {
            copyAction.setEnabled(EditorUtil.hasActiveSelection(editor.getContext()));
        }
    }
}
