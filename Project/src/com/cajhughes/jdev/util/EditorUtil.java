package com.cajhughes.jdev.util;

import com.cajhughes.jdev.copy.model.TextBufferHelper;
import oracle.ide.Context;
import oracle.ide.ceditor.CodeEditor;
import oracle.ide.model.TextNode;
import oracle.ide.view.View;
import oracle.javatools.buffer.TextBuffer;
import oracle.javatools.editor.EditorProperties;

public final class EditorUtil {
    private static final int NO_CARET_LINE = -1;

    private static int getCaretLine(final Context context) {
        int result = NO_CARET_LINE;
        CodeEditor ceditor = getCodeEditor(context);
        if (ceditor != null) {
            result = (ceditor.getCaretLine() - 1);
        }
        return result;
    }

    private static String getCaretLine(final Context context, final int line) {
        String result = null;
        if (context != null) {
            if (NodeUtil.isTextNode(context)) {
                TextNode node = (TextNode)context.getNode();
                if (node != null) {
                    try {
                        TextBuffer buffer = node.acquireTextBuffer();
                        if (buffer != null) {
                            TextBufferHelper helper = new TextBufferHelper(buffer);
                            StringBuffer stringBuffer = helper.getLine(line);
                            if (stringBuffer != null) {
                                result = stringBuffer.toString();
                            }
                        }
                    }
                    finally {
                        node.releaseTextBuffer();
                    }
                }
            }
        }
        return result;
    }

    private static CodeEditor getCodeEditor(final Context context) {
        CodeEditor ceditor = null;
        if (context != null) {
            View view = context.getView();
            if (view != null && view instanceof CodeEditor) {
                ceditor = (CodeEditor)view;
            }
        }
        return ceditor;
    }

    public static String getSelectedText(final CodeEditor editor) {
        String result = null;
        if (editor != null) {
            result = editor.getSelectedText();
        }
        if (result == null) {
            EditorProperties properties = EditorProperties.getProperties();
            if (properties != null) {
                boolean copyLine = properties.getBooleanProperty(EditorProperties.PROPERTY_CUT_COPY_LINE);
                if (copyLine) {
                    Context context = editor.getContext();
                    int line = getCaretLine(context);
                    if (line != NO_CARET_LINE) {
                        result = getCaretLine(context, line);
                    }
                }
            }
        }
        return result;
    }
    public static String getSelectedText(final Context context) {
        String result = null;
        if (context != null) {
            View view = context.getView();
            if (view != null && view instanceof CodeEditor) {
                CodeEditor ceditor = (CodeEditor)view;
                result = getSelectedText(ceditor);
            }
        }
        if (result == null) {
            EditorProperties properties = EditorProperties.getProperties();
            if (properties != null) {
                boolean copyLine = properties.getBooleanProperty(EditorProperties.PROPERTY_CUT_COPY_LINE);
                if (copyLine) {
                    int line = getCaretLine(context);
                    if (line != NO_CARET_LINE) {
                        result = getCaretLine(context, line);
                    }
                }
            }
        }
        return result;
    }

    public static boolean hasActiveSelection(final Context context) {
        String selectedText = getSelectedText(context);
        return (selectedText != null);
    }
}
