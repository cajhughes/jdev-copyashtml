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
import oracle.javatools.editor.language.BaseStyle;
import oracle.javatools.editor.language.DocumentRenderer;
import oracle.javatools.editor.language.StyledFragment;
import oracle.javatools.editor.language.StyledFragmentsList;

public class RtfGenerator extends Generator {
    private CopyPreferences preferences;
    private List<BaseStyle> styleTable = null;

    public RtfGenerator(final BasicDocument document, final CopyPreferences prefs) {
        super(document);
        preferences = prefs;
    }

    public void generateRTF(final Writer writer) throws IOException {
        TextBuffer textBuffer = document.getTextBuffer();
        DocumentRenderer renderer = document.getDocumentRenderer();
        LineMap lineMap = textBuffer.getLineMap();
        int lineCount = lineMap.getLineCount();
        StyledFragmentsList fragmentList = renderer.renderLines(0, lineCount - 1);
        styleTable = generateStyleTable(fragmentList);
        int numFragments = fragmentList.size();
        writer.write("{\\rtf1\\ansi{\\fonttbl\\f0\\fnil\\fcharset0 " + preferences.getFontFamily() + ";}");
        writeColorTable(writer);
        writer.write("{\\pard\\f0 ");
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

    protected List<BaseStyle> generateStyleTable(final StyledFragmentsList fragmentList) {
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

    protected void writeColorTable(final Writer writer) throws IOException {
        if (styleTable != null) {
            writer.write("{\\colortbl;");
            for (BaseStyle style : styleTable) {
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
                int colorIndex = styleTable.indexOf(style);
                writer.write("{");
                if ((style.getFontStyle() & Font.BOLD) > 0) {
                    writer.write("\\b");
                }
                if ((style.getFontStyle() & Font.ITALIC) > 0) {
                    writer.write("\\i");
                }
                if (colorIndex >= 0) {
                    // Color Table references seem to be 1-based, so add 1 to the index
                    writer.write("\\cf" + (colorIndex + 1));
                }
                if (preferences != null) {
                    writer.write("\\fs" + (preferences.getFontSize() * 2));
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
