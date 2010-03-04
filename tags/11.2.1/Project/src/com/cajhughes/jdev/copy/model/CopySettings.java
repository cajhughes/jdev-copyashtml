package com.cajhughes.jdev.copy.model;

import com.cajhughes.jdev.copy.CopyCommand;
import oracle.ide.config.ClientSetting;

/**
 * Helper class which provides static methods for storing and getting the
 * preferred HTML format for copying.
 *
 * @author Chris Hughes
 */
public final class CopySettings {
    public static CopyPreferences getCurrent() {
        final ClientSetting setting = ClientSetting.findOrCreate(CopyCommand.EXTENSION_ID);
        CopyPreferences preferences = (CopyPreferences)setting.getData(CopyPreferences.KEY);
        return preferences;
    }

    public static void initialize() {
        final ClientSetting setting = ClientSetting.findOrCreate(CopyCommand.EXTENSION_ID);
        CopyPreferences preferences = (CopyPreferences)setting.getData(CopyPreferences.KEY);
        if (preferences == null) {
            preferences = new CopyPreferences();
            setting.putData(CopyPreferences.KEY, preferences);
        }
    }
}
