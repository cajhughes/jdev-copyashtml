package com.cajhughes.jdev.copy;

import com.cajhughes.jdev.copy.controller.CopyEditorListener;
import com.cajhughes.jdev.copy.view.CopyKeyStrokeContext;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.controller.IdeAction;
import oracle.ide.editor.EditorManager;
import oracle.ide.keyboard.KeyStrokes;

public final class CopyAddin implements Addin {
    private static final CopyEditorListener editorListener = new CopyEditorListener();
    private static final KeyStroke copyShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK);

    public void initialize() {
        EditorManager.getEditorManager().addEditorListener(editorListener);
        CopyKeyStrokeContext copyContext = new CopyKeyStrokeContext();
        copyContext.add(IdeAction.find(CopyCommand.actionId()), new KeyStrokes(copyShortcut));
        Ide.getKeyStrokeContextRegistry().addContext(copyContext);
    }
}
