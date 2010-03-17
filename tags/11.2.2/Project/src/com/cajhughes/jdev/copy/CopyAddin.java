package com.cajhughes.jdev.copy;

import com.cajhughes.jdev.copy.controller.CopyEditorListener;
import com.cajhughes.jdev.copy.model.CopySettings;
import com.cajhughes.jdev.copy.view.CopyKeyStrokeContext;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.controller.IdeAction;
import oracle.ide.editor.EditorManager;
import oracle.ide.keyboard.KeyStrokes;

/**
 * This class implements the Addin interface, and exists to perform the
 * initialization required for the CopyAsHtml extension to work correctly.
 *
 * (a) It initializes the HTML output format
 * (b) It registers an EditorListener, to track cursor movement
 * (c) It registers a default shortcut key for the action
 *
 * @author Chris Hughes
 */
public final class CopyAddin implements Addin {
    private static final CopyEditorListener editorListener = new CopyEditorListener();
    private static final KeyStroke copyShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);

    @Override
    public void initialize() {
        // Initialise extension settings
        CopySettings.initialize();

        // Register our editor listener, so that we can track new code editors being opened
        EditorManager.getEditorManager().addEditorListener(editorListener);

        // Define the default shortcut key as Ctrl-H
        CopyKeyStrokeContext copyContext = new CopyKeyStrokeContext();
        copyContext.add(IdeAction.find(CopyCommand.actionId()), new KeyStrokes(copyShortcut));
        Ide.getKeyStrokeContextRegistry().addContext(copyContext);
    }
}
