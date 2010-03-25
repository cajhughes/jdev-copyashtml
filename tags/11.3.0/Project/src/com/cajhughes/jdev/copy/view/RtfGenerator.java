package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.model.CopyPreferences;
import java.awt.Font;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import oracle.javatools.buffer.LineMap;
import oracle.javatools.buffer.TextBuffer;
import oracle.javatools.editor.BasicDocument;
import oracle.javatools.editor.EditorProperties;
import oracle.javatools.editor.language.BaseStyle;
import oracle.javatools.editor.language.DocumentRenderer;
import oracle.javatools.editor.language.StyledFragment;
import oracle.javatools.editor.language.StyledFragmentsList;

public class RtfGenerator extends Generator {
    private List<BaseStyle> baseStyles = null;

    public RtfGenerator(final BasicDocument document) {
        super(document);
    }

    public void generateRTF(final Writer writer, final CopyPreferences prefs) throws IOException {
        TextBuffer textBuffer = document.getTextBuffer();
        DocumentRenderer renderer = document.getDocumentRenderer();
        LineMap lineMap = textBuffer.getLineMap();
        int lineCount = lineMap.getLineCount();
        StyledFragmentsList fragmentList = renderer.renderLines(0, lineCount - 1);
        baseStyles = getBaseStyles(fragmentList);
        int numFragments = fragmentList.size();
        writer.write("{\\rtf1\\ansi{\\fonttbl{\\f0\\fnil " + getFontFamily(prefs) + ";}}");
        writeColorTable(writer);
        writer.write("{\\pard\\f0\\fs" + (getFontSize(prefs)*2) + " ");
        for (int i = 0; i < numFragments; i++) {
            StyledFragment fragment = fragmentList.get(i);
            if (fragment != null) {
                writeStart(writer, fragment.styleName);
                for (int j = fragment.startOffset; j < fragment.endOffset; j++) {
                    writeChar(writer, textBuffer.getChar(j));
                }
                writeEnd(writer);
            }
        }
        writer.write("\\par}}");
    }

    protected List<BaseStyle> getBaseStyles(final StyledFragmentsList fragmentList) {
        List<BaseStyle> colorTable = new ArrayList<BaseStyle>();
        if (fragmentList != null) {
            int numFragments = fragmentList.size();
            for (int i = 0; i < numFragments; i++) {
                StyledFragment fragment = fragmentList.get(i);
                if (fragment != null) {
                    BaseStyle style = getStyle(fragment.styleName);
                    if (!colorTable.contains(style)) {
                        colorTable.add(style);
                    }
                }
            }
        }
        return colorTable;
    }

    protected String getFontFamily(final CopyPreferences prefs) {
        String result = null;
        if (prefs != null) {
            result = prefs.getFontFamily();
        }
        if (result == null) {
            EditorProperties properties = EditorProperties.getProperties();
            if (properties != null) {
                result = properties.getBaseFont().getFamily();
            }
        }
        return result;
    }

    protected int getFontSize(final CopyPreferences prefs) {
        int result = 0;
        if (prefs != null) {
            result = prefs.getFontSize();
        }
        if (result == 0) {
            EditorProperties properties = EditorProperties.getProperties();
            if (properties != null) {
                result = properties.getBaseFont().getSize();
            }
        }
        return result;
    }

    protected void writeColorTable(final Writer writer) throws IOException {
        if (baseStyles != null) {
            writer.write("{\\colortbl;");
            for (BaseStyle style : baseStyles) {
                writer.write("\\red" + style.getForegroundColor().getRed());
                writer.write("\\green" + style.getForegroundColor().getGreen());
                writer.write("\\blue" + style.getForegroundColor().getBlue() + ";");
            }
            writer.write("}");
        }
    }

    protected void writeStart(final Writer writer, final String styleName) throws IOException {
        if (registry != null) {
            BaseStyle style = getStyle(styleName);
            if (style != null) {
                int colorIndex = baseStyles.indexOf(style);
                writer.write("{");
                if ((style.getFontStyle() & Font.BOLD) > 0) {
                    writer.write("\\b");
                }
                if ((style.getFontStyle() & Font.ITALIC) > 0) {
                    writer.write("\\i");
                }
                if (colorIndex >= 0) {
                    // Color Table references are 1-based, so add 1 to the index
                    writer.write("\\cf" + (colorIndex + 1));
                }
                writer.write(" ");
            }
        }
    }

    protected void writeEnd(final Writer writer) throws IOException {
        writer.write("}");
    }

    protected void writeChar(final Writer writer, final char c) throws IOException {
        switch (c) {
            case 9:
                writer.write("\\tab ");
                break;
            case 10:
            case 13:
                writer.write("\\line ");
                break;
            case 92:
                writer.write("\\\\");
                break;
            case 123:
                writer.write("\\{");
                break;
            case 125:
                writer.write("\\}");
                break;
            default:
                writer.write(c);
                break;
        }
    }
}
