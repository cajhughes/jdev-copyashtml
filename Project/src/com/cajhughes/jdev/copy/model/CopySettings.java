package com.cajhughes.jdev.copy.model;

import oracle.ide.config.ClientSetting;

public final class CopySettings {
    public static CopyPreferences getCurrent(final String extension) {
        final ClientSetting setting = ClientSetting.findOrCreate(extension);
        CopyPreferences preferences = (CopyPreferences)setting.getData(CopyPreferences.KEY);
        if (preferences == null) {
            initialize(extension);
            return getCurrent(extension);
        }
        else {
            return preferences;
        }
    }

    public static void initialize(final String extension) {
        final ClientSetting setting = ClientSetting.findOrCreate(extension);
        CopyPreferences preferences = (CopyPreferences)setting.getData(CopyPreferences.KEY);
        if (preferences == null) {
            preferences = new CopyPreferences();
            setting.putData(CopyPreferences.KEY, preferences);
        }
    }
}
