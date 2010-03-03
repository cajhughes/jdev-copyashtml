package com.cajhughes.jdev.copy.controller;

import oracle.ide.ceditor.CodeEditor;
import oracle.ide.editor.Editor;
import oracle.ide.editor.EditorListener;

/**
 * This class implements the EditorListener interface, and exists so that we
 * can associate our extension-specific CursorListener with every CodeEditor
 * that is opened.
 *
 * @author Chris Hughes
 */
public final class CopyEditorListener implements EditorListener {
    @Override
    public void editorOpened(Editor editor) {
        if (editor != null && editor instanceof CodeEditor) {
            CodeEditor ceditor = (CodeEditor)editor;
            ceditor.addCursorListener(new CopyCursorListener());
        }
    }

    @Override
    public void editorActivated(Editor editor) {
    }

    @Override
    public void editorDeactivated(Editor editor) {
    }

    @Override
    public void editorClosed(Editor editor) {
    }
}
