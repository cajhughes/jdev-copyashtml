package com.cajhughes.jdev.util;

import oracle.ide.Context;
import oracle.ide.model.Node;
import oracle.ide.model.TextNode;

public final class NodeUtil {
    public static boolean isTextNode(final Context context) {
        boolean result = false;
        if (context != null) {
            Node node = context.getNode();
            result = ((node != null) && (node instanceof TextNode));
        }
        return result;
    }
}
