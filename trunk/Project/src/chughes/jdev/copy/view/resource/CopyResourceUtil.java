package chughes.jdev.copy.view.resource;

import java.util.ResourceBundle;

/*
 * @author Chris Hughes
 *
 * Utility class to provide static access to resource strings.
 */
public class CopyResourceUtil
{
    private static final ResourceBundle bundle =
        ResourceBundle.getBundle(CopyResourceKeys.COPY_RESOURCES);

    public static String getString (final String key)
    {
        return bundle.getString(key);
    }
}