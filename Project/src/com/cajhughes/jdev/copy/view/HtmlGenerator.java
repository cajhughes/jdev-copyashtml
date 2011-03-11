package com.cajhughes.jdev.copy.view;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.Writer;
import oracle.javatools.editor.BasicDocument;
import oracle.javatools.editor.EditorProperties;
import oracle.javatools.editor.Utilities;
import oracle.javatools.editor.language.BaseStyle;

/**
 * This class generates the HTML equivalent for the snippet of text selected.
 * It supports all generation types currently defined.
 *
 * @author Chris Hughes
 */
public abstract class HtmlGenerator extends Generator {
    protected int tabSize = 0;

    public HtmlGenerator(final BasicDocument document) {
        super(document);
        EditorProperties properties = EditorProperties.getProperties();
        if (properties != null) {
            tabSize = properties.getIntegerProperty(EditorProperties.PROPERTY_TAB_SIZE);
        }
    }

    protected int writeChar(final Writer writer, final char c, final int column,
                            final boolean escapeSpace) throws IOException {
        int result = column;
        switch (c) {
            case 9:
                int nextStop = Utilities.getNextTabStop(tabSize, column);
                int spaces = nextStop - column;
                for (int s = 0; s < spaces; s++) {
                    if (escapeSpace) {
                        writer.write("&nbsp;");
                    }
                    else {
                        writer.write(32);
                    }
                    result++;
                }
                break;
            case 10:
            case 13:
                writer.write(c);
                result = 0;
                break;
            case 34:
                writer.write("&quot;");
                result++;
                break;
            case 38:
                writer.write("&amp;");
                result++;
                break;
            case 60:
                writer.write("&lt;");
                result++;
                break;
            case 62:
                writer.write("&gt;");
                result++;
                break;
            default:
                writer.write(c);
                result++;
                break;
        }
        return result;
    }

    protected void writeColor(final Writer writer, final Color color) throws IOException {
        int value = color.getRGB() & 0xffffff;
        String hexValue = Integer.toHexString(value);
        writer.write("#");
        int hexLen = hexValue.length();
        int paddingToWrite = 6 - hexLen;
        for (int i = 0; i < paddingToWrite; i++) {
            writer.write(48);
        }
        writer.write(hexValue);
    }

    protected void writeFontStart(final Writer writer, final String styleName) throws IOException {
        if (registry != null) {
            BaseStyle style = getStyle(styleName);
            if (style != null) {
                writer.write("<font color=\"");
                writeColor(writer, style.getForegroundColor());
                writer.write("\">");
                if ((style.getFontStyle() & Font.BOLD) > 0) {
                    writer.write("<strong>");
                }
                if ((style.getFontStyle() & Font.ITALIC) > 0) {
                    writer.write("<em>");
                }
            }
        }
    }

    protected void writeFontEnd(final Writer writer, final String styleName) throws IOException {
        if (registry != null) {
            BaseStyle style = getStyle(styleName);
            if (style != null) {
                if ((style.getFontStyle() & Font.ITALIC) > 0) {
                    writer.write("</em>");
                }
                if ((style.getFontStyle() & Font.BOLD) > 0) {
                    writer.write("</strong>");
                }
                writer.write("</font>");
            }
        }
    }

    protected void writeSpanStart(final Writer writer, final String styleName) throws IOException {
        if (registry != null) {
            BaseStyle style = getStyle(styleName);
            if (style != null) {
                writer.write("<span style=\"color:");
                writeColor(writer, style.getForegroundColor());
                writer.write("\">");
                if ((style.getFontStyle() & Font.BOLD) > 0) {
                    writer.write("<strong>");
                }
                if ((style.getFontStyle() & Font.ITALIC) > 0) {
                    writer.write("<em>");
                }
            }
        }
    }

    protected void writeSpanEnd(final Writer writer, final String styleName) throws IOException {
        if (registry != null) {
            BaseStyle style = getStyle(styleName);
            if (style != null) {
                if ((style.getFontStyle() & Font.ITALIC) > 0) {
                    writer.write("</em>");
                }
                if ((style.getFontStyle() & Font.BOLD) > 0) {
                    writer.write("</strong>");
                }
                writer.write("</span>");
            }
        }
    }
}
