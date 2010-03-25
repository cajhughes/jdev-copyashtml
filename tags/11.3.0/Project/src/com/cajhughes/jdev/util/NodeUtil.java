package com.cajhughes.jdev.util;

import oracle.ide.Context;
import oracle.ide.model.Node;
import oracle.ide.model.TextNode;

/**
 * This class provides static helper methods for identifying nodes within
 * the JDeveloper framework.
 *
 * @author Chris Hughes
 */
public final class NodeUtil {
    public static boolean isTextNode(final Context context) {
        boolean result = false;
        if (context != null) {
            Node node = context.getNode();
            result = ((node != null) && (node instanceof TextNode));
        }
        return result;
    }

    public static boolean isEditableTextNode(final Context context) {
        boolean result = false;
        if (isTextNode(context)) {
            result = !context.getNode().isReadOnly();
        }
        return result;
    }
}
