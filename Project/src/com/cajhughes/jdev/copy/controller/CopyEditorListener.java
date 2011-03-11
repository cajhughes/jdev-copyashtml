package com.cajhughes.jdev.copy.controller;

import com.cajhughes.jdev.copy.CopyCommand;
import com.cajhughes.jdev.util.EditorUtil;
import oracle.ide.ceditor.CodeEditor;
import oracle.ide.editor.Editor;
import oracle.ide.editor.EditorListener;
import oracle.ide.log.LogManager;
import oracle.javatools.editor.BasicEditorPane;

/**
 * This class implements the EditorListener interface, and exists so that we
 * can add a caret/cursor listener, so that we are notified every time the
 * caret/cursor moves; so that we can determine whether the copy operation
 * should be enabled.
 *
 * @author Chris Hughes
 */
public final class CopyEditorListener implements EditorListener {
    private static boolean worksheetWarning = false;

    @Override
    public void editorOpened(Editor editor) {
        if (editor != null) {
            // If the current editor is a CodeEditor, then add a cursor listener
            if (editor instanceof CodeEditor) {
                ((CodeEditor)editor).addCursorListener(new CopyCursorListener());
            }
            else {
                // If the current editor is a Worksheet, then add a caret listener
                try {
                    if (Class.forName(EditorUtil.WORKSHEET_CLASS).isInstance(editor)) {
                        BasicEditorPane basic =
                            ((oracle.dbtools.worksheet.editor.Worksheet)editor).getFocusedEditorPane();
                        if (basic != null) {
                            basic.addCaretListener(new CopyCaretListener(editor));
                        }
                    }
                }
                catch (ClassNotFoundException cnfe) {
                    // Can get here if the Worksheet class is not in the classpath,
                    // which can occur, for example, if running JDeveloper Java Edition
                    if (!worksheetWarning) {
                        worksheetWarning = true;
                        String warning = CopyCommand.EXTENSION_ID + ": " + EditorUtil.WORKSHEET_CLASS + " not found";
                        LogManager.getLogManager().getMsgPage().log(warning);
                    }
                }
            }
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
