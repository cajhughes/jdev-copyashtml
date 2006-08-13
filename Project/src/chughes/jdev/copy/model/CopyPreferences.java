package chughes.jdev.copy.model;

import oracle.ide.util.Copyable;

/*
 * @author Chris Hughes
 *
 * This class acts as a bean to store the copy format specified via the
 * Tools -> Preferences dialog.  It implements Copyable so that the IDE
 * can inherently store the setting out to a file for us.
 */
public final class CopyPreferences implements Copyable
{
    public static final String KEY = "CopyPreferences";
    public static final int CODE = 0;
    public static final int PRE = 1;
    public static final int FULL = 2;

    private int copyFormat;

    public CopyPreferences()
    {
        super();
        copyFormat = CODE;
    }

    public Object copyTo (final Object target)
    {
        CopyPreferences copy = null;
        if (target == null)
        {
            copy = new CopyPreferences();
        }
        else
        {
            copy = (CopyPreferences)target;
        }
        copy.setCopyFormat(this.copyFormat);
        return copy;
    }

    public int getCopyFormat ()
    {
        return copyFormat;
    }

    public void setCopyFormat (final int format)
    {
        copyFormat = format;
    }
}