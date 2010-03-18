package com.cajhughes.jdev.copy.model;

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

    public CopyPreferences() {
        super();
        copyFormat = CODEMARKUP;
    }

    @Override
    public Object copyTo(final Object target) {
        final CopyPreferences copy = (target != null) ? (CopyPreferences)target : new CopyPreferences();
        copyToImpl(copy);
        return copy;
    }

    protected final void copyToImpl(CopyPreferences copy) {
        copy.copyFormat = this.copyFormat;
    }

    public int getCopyFormat() {
        return copyFormat;
    }

    public void setCopyFormat(final int format) {
        copyFormat = format;
    }
}
