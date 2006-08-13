package chughes.jdev.util;

import chughes.jdev.copy.model.TextBufferHelper;
import oracle.ide.Context;
import oracle.ide.ceditor.CodeEditor;
import oracle.ide.ceditor.find.FindableEditor;
import oracle.ide.view.View;
import oracle.ide.model.TextNode;
import oracle.javatools.buffer.TextBuffer;
import oracle.javatools.editor.BasicEditorPane;
import oracle.javatools.editor.EditorProperties;

/*
 * @author Chris Hughes
 *
 * This class provides helper methods for interacting with JDeveloper editors.
 */
public final class EditorUtil
{
    protected static final int NO_CARET_LINE = -1;

    /*
     * Determines the focussed pane, if an editor has focus within the IDE.
     */
    public static BasicEditorPane getBasicEditorPane (final Context context)
    {
        BasicEditorPane pane = null;
        if (context != null)
        {
            View view = context.getView();
            if (view != null && view instanceof FindableEditor)
            {
                FindableEditor findable = (FindableEditor)view;
                pane = findable.getFocusedEditorPane();
            }
        }
        return pane;
    }

    /*
     * Determines the CodeEditor instance associated with the current editor
     * within the IDE.
     */
    public static CodeEditor getCodeEditor (final Context context)
    {
        CodeEditor codeEditor = null;
        if (context != null)
        {
            BasicEditorPane pane = getBasicEditorPane(context);
            if (pane != null)
            {
                codeEditor = CodeEditor.getCodeEditor(pane);
            }
        }
        return codeEditor;
    }

    /*
     * Determine the zero-based line number that represents the current
     * position of the caret within the editor.
     */
    public static int getCaretLine (final Context context)
    {
        int result = NO_CARET_LINE;
        if (context != null)
        {
            CodeEditor codeEditor = getCodeEditor(context);
            if (codeEditor != null)
            {
                result = (codeEditor.getCaretLine() - 1);
            }
        }
        return result;
    }

    /*
     * Helper method to obtain the text of the current line in which the
     * caret is positioned.
     */
    public static String getCaretLine (final Context context, final int line)
    {
        String result = null;
        if (context != null)
        {
            if (NodeUtil.isTextNode(context))
            {
                TextNode node = (TextNode)context.getElement();
                if (node != null)
                {
                    try
                    {
                        TextBuffer buffer = node.acquireTextBuffer();
                        if (buffer != null)
                        {
                            TextBufferHelper helper =
                                new TextBufferHelper(buffer);
                            StringBuffer sBuffer = helper.getLine(line);
                            if (sBuffer != null)
                            {
                                result = sBuffer.toString();
                            }
                        }
                    }
                    finally
                    {
                        node.releaseTextBuffer();
                    }
                }
            }
        }
        return result;
    }

    /*
     * Helper method for determining the currently selected text in the
     * currently active editor.
     */
    public static String getSelectedText (final Context context)
    {
        String result = null;
        if (context != null)
        {
            BasicEditorPane pane = getBasicEditorPane(context);
            if (pane != null)
            {
                result = pane.getSelectedText();
                if (result == null)
                {
                    EditorProperties properties =
                        EditorProperties.getProperties();
                    if (properties != null)
                    {
                        boolean copyLine =
                            properties.getBooleanProperty(
                                EditorProperties.PROPERTY_CUT_COPY_LINE);
                        if (copyLine)
                        {
                            int line = getCaretLine(context);
                            if (line != NO_CARET_LINE)
                            {
                                result = getCaretLine(context, line);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /*
     * Helper method to determine if there is an active selection
     */
    public static boolean hasActiveSelection (final Context context)
    {
        String selectedText = getSelectedText(context);
        return (selectedText != null);
    }
}