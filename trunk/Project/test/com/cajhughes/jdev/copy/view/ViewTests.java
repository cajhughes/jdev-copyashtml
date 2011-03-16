package com.cajhughes.jdev.copy.view;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { RtfGeneratorTest.class, HtmlPreGeneratorTest.class,
                       HtmlCodeMarkupGeneratorTest.class,
                       HtmlCodeGeneratorTest.class })
public class ViewTests {
    public static void main(String[] args) {
        String[] args2 = { ViewTests.class.getName() };
        JUnitCore.main(args2);
    }
}
