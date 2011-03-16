package com.cajhughes.jdev.copy.view;

import java.io.IOException;
import java.io.StringWriter;

import oracle.javatools.buffer.TextBuffer;

import oracle.javatools.buffer.TextBufferFactory;

import oracle.javatools.editor.BasicDocument;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RtfGeneratorTest {
    private RtfGenerator generator;
    private static final String JAVA_CODE_TEXT =
        "{\\rtf1\\ansi{\\fonttbl{\\f0\\fnil DialogInput;}}{\\colortbl;\\red51\\green51\\blue51;}{\\pard\\f0\\fs24 {\\cf1 final StringWriter writer = new StringWriter();}\\par}}";
    private static final String JAVA_TEXT = "final StringWriter writer = new StringWriter();";

    @Before
    public void setUp() throws Exception {
        TextBuffer buffer = TextBufferFactory.createTextBuffer();
        buffer.insert(0, JAVA_TEXT.toCharArray());
        BasicDocument document = new BasicDocument(buffer);
        generator = new RtfGenerator(document);
    }

    @After
    public void tearDown() throws Exception {
        generator = null;
    }

    /**
     * @see HtmlCodeMarkupGenerator#generate(java.io.Writer,com.cajhughes.jdev.copy.model.CopyPreferences)
     */
    @Test
    public void testGenerate() {
        StringWriter writer = new StringWriter();
        try {
            generator.generate(writer, null);
            StringBuffer buffer = writer.getBuffer();
            assertEquals(buffer.toString(), JAVA_CODE_TEXT);
        }
        catch(IOException ioe) {
            fail("IOException thrown");
        }
    }
}
