package chughes.jdev.util;

import javax.ide.model.Document;
import oracle.ide.Context;
import oracle.ide.model.Element;
import oracle.ide.model.TextNode;

/*
 * @author Chris Hughes
 *
 * This class provides static helper methods for identifying nodes within
 * the JDeveloper framework.
 */
public final class NodeUtil
{
    public static boolean isTextNode (final Context context)
    {
        boolean result = false;
        if (context != null)
        {
            Element element = context.getElement();
            result = ((element != null) && (element instanceof TextNode));
        }
        return result;
    }
}