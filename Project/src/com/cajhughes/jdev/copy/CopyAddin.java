package com.cajhughes.jdev.copy;

import com.cajhughes.jdev.copy.controller.CopyEditorListener;
import oracle.ide.Addin;
import oracle.ide.editor.EditorManager;

public final class CopyAddin implements Addin {
    public void initialize() {
        EditorManager.getEditorManager().addEditorListener(new CopyEditorListener());
    }
}
