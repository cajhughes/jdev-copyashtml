package com.cajhughes.jdev.copy.model;

import java.awt.Font;
import oracle.javatools.editor.EditorProperties;
import oracle.javatools.util.Copyable;

/**
 * This class implements the Copyable interface, so that the Preferences
 * dialog can operate on a copy of the extension data.
 *
 * @author Chris Hughes
 */
public final class CopyPreferences implements Copyable {
    public static final String KEY = "CopyPreferences";
    public static final int CODE = 0;
    public static final int PRE = 1;
    public static final int FULL = 2;
    public static final int CODEMARKUP = 3;
    public static final int RTF = 4;

    private int copyFormat;
    private String fontFamily;
    private int fontSize;

    public CopyPreferences() {
        super();
        copyFormat = CODEMARKUP;
        EditorProperties properties = EditorProperties.getProperties();
        if (properties != null) {
            Font font = properties.getBaseFont();
            if (font != null) {
                fontFamily = font.getFamily();
                fontSize = font.getSize();
            }
        }
    }

    @Override
    public Object copyTo(final Object target) {
        final CopyPreferences copy = (target != null) ? (CopyPreferences)target : new CopyPreferences();
        copyToImpl(copy);
        return copy;
    }

    protected final void copyToImpl(CopyPreferences copy) {
        copy.copyFormat = this.copyFormat;
        copy.fontFamily = this.fontFamily;
        copy.fontSize = this.fontSize;
    }

    public int getCopyFormat() {
        return copyFormat;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setCopyFormat(final int format) {
        copyFormat = format;
    }

    public void setFontFamily(final String family) {
        fontFamily = family;
    }

    public void setFontSize(final int size) {
        fontSize = size;
    }
}
