package com.cajhughes.jdev.copy.controller;

import oracle.ide.ceditor.CodeEditor;
import oracle.ide.editor.Editor;
import oracle.ide.editor.EditorListener;

public final class CopyEditorListener implements EditorListener {
    public void editorOpened(Editor editor) {
        if (editor != null && editor instanceof CodeEditor) {
            CodeEditor ceditor = (CodeEditor)editor;
            ceditor.addCursorListener(new CopyCursorListener());
        }
    }

    public void editorActivated(Editor editor) {
    }

    public void editorDeactivated(Editor editor) {
    }

    public void editorClosed(Editor editor) {
    }
}
