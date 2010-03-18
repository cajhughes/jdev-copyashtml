package com.cajhughes.jdev.copy.view.resource;

import java.util.ResourceBundle;

/**
 * This class provides a static method to access the Resource.properties file
 * for the extension.
 *
 * @author Chris Hughes
 */
public final class CopyResourceUtil {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.cajhughes.jdev.copy.Resource");

    public static String getString(final String key) {
        return bundle.getString(key);
    }
}
