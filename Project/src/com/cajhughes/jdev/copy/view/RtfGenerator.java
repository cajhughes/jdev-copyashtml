package com.cajhughes.jdev.copy.view;

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
    private List<BaseStyle> styleTable = null;
    private Font baseFont = Font.getFont(Font.DIALOG_INPUT);

    public RtfGenerator(final BasicDocument document) {
        super(document);
        EditorProperties properties = EditorProperties.getProperties();
        if (properties != null) {
            baseFont = properties.getBaseFont();
        }
    }

    public void generateRTF(final Writer writer) throws IOException {
        TextBuffer textBuffer = document.getTextBuffer();
        DocumentRenderer renderer = document.getDocumentRenderer();
        LineMap lineMap = textBuffer.getLineMap();
        int lineCount = lineMap.getLineCount();
        StyledFragmentsList fragmentList = renderer.renderLines(0, lineCount - 1);
        styleTable = generateStyleTable(fragmentList);
        int numFragments = fragmentList.size();
        writer.write("{\\rtf1\\ansi\\deff0{\\fonttbl{\\f0 Courier New;}}");
        writeColorTable(writer);
        writer.write("{\\pard");
        int columnCount = 0;
        for (int i = 0; i < numFragments; i++) {
            StyledFragment fragment = fragmentList.get(i);
            if (fragment != null) {
                int startOffset = fragment.startOffset;
                int endOffset = fragment.endOffset;
                String styleName = fragment.styleName;
                writeStart(writer, styleName);
                for (int j = startOffset; j < endOffset; j++) {
                    columnCount = writeChar(writer, textBuffer.getChar(j), columnCount);
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
                    writer.write("\\cf" + colorIndex);
                }
                if (baseFont != null) {
                    writer.write("\\fs" + (baseFont.getSize() * 2));
                }
                writer.write(" ");
            }
        }
    }

    protected void writeEnd(final Writer writer) throws IOException {
        writer.write("}");
    }

    protected int writeChar(final Writer writer, final char c, final int column) throws IOException {
        int result = column;
        switch (c) {
            case 9:
                writer.write("\\tab ");
                result++;
                break;
            case 10:
            case 13:
                writer.write("\\line ");
                result = 0;
                break;
            case 92:
                writer.write("\\");
                result++;
                break;
            case 123:
                writer.write("\\{");
                result++;
                break;
            case 125:
                writer.write("\\}");
                result++;
                break;
            default:
                writer.write(c);
                result++;
                break;
        }
        return result;
    }
}
