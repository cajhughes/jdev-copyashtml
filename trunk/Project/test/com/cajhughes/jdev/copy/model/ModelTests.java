package com.cajhughes.jdev.copy.model;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CopyPreferencesTest.class })
public class ModelTests {
    public static void main(String[] args) {
        String[] args2 = { ModelTests.class.getName() };
        JUnitCore.main(args2);
    }
}
