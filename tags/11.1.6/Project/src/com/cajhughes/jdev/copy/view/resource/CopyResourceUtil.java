package com.cajhughes.jdev.copy.view.resource;

import java.util.ResourceBundle;

public final class CopyResourceUtil {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.cajhughes.jdev.copy.Resource");

    public static String getString(final String key) {
        return bundle.getString(key);
    }
}
