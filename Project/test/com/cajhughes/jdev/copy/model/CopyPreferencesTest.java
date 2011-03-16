package com.cajhughes.jdev.copy.model;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CopyPreferencesTest {
    private CopyPreferences preferences;
    private static final String Consolas = "Consolas";
    private static final String DialogInput = "DialogInput";

    @Before
    public void setUp() throws Exception {
        preferences = new CopyPreferences();
    }

    @After
    public void tearDown() throws Exception {
        preferences = null;
    }

    /**
     * @see CopyPreferences#getCopyFormat()
     */
    @Test
    public void testGetCopyFormat() {
        preferences.setCopyFormat(CopyPreferences.CODE);
        assertEquals(preferences.getCopyFormat(), CopyPreferences.CODE);
        preferences.setCopyFormat(CopyPreferences.RTF);
        assertEquals(preferences.getCopyFormat(), CopyPreferences.RTF);
    }

    /**
     * @see CopyPreferences#getFontFamily()
     */
    @Test
    public void testGetFontFamily() {
        preferences.setFontFamily(DialogInput);
        assertEquals(preferences.getFontFamily(), DialogInput);
        preferences.setFontFamily(Consolas);
        assertEquals(preferences.getFontFamily(), Consolas);
    }

    /**
     * @see CopyPreferences#getFontSize()
     */
    @Test
    public void testGetFontSize() {
        preferences.setFontSize(12);
        assertEquals(preferences.getFontSize(), 12);
        preferences.setFontSize(10);
        assertEquals(preferences.getFontSize(), 10);
    }
}
