package chughes.jdev.copy.model;

import oracle.ide.config.ClientSetting;

/*
 * @author Chris Hughes
 *
 * This class centralises the initialisation work required for the model
 * parts of the extension:
 *
 * (a) Reads in the preferences, and if not found initialises them with
 *     their default values
 */
public final class Settings
{
    public static CopyPreferences getCurrent (final String extension)
    {
        final ClientSetting setting = ClientSetting.findOrCreate(extension);
        return (CopyPreferences)setting.getData(CopyPreferences.KEY);
    }

    public static void initialize (final String extension)
    {
        final ClientSetting setting = ClientSetting.findOrCreate(extension);
        CopyPreferences preferences =
            (CopyPreferences)setting.getData(CopyPreferences.KEY);
        if (preferences == null)
        {
            preferences = new CopyPreferences();
            setting.putData(CopyPreferences.KEY, preferences);
        }
    }
}