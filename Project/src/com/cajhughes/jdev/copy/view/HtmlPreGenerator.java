package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.model.CopyPreferences;
import java.io.IOException;
import java.io.Writer;
import oracle.javatools.buffer.LineMap;
import oracle.javatools.buffer.TextBuffer;
import oracle.javatools.editor.BasicDocument;
import oracle.javatools.editor.language.DocumentRenderer;
import oracle.javatools.editor.language.StyledFragment;
import oracle.javatools.editor.language.StyledFragmentsList;

public class HtmlPreGenerator extends HtmlGenerator {
    public HtmlPreGenerator(final BasicDocument document) {
        super(document);
    }

    protected void generate(final Writer writer, final CopyPreferences prefs) throws IOException {
        TextBuffer textBuffer = document.getTextBuffer();
        DocumentRenderer renderer = document.getDocumentRenderer();
        LineMap lineMap = textBuffer.getLineMap();
        int lineCount = lineMap.getLineCount();
        StyledFragmentsList fragmentList = renderer.renderLines(0, lineCount - 1);
        int numFragments = fragmentList.size();
        writer.write("<pre>");
        int columnCount = 0;
        for (int i = 0; i < numFragments; i++) {
            StyledFragment fragment = fragmentList.get(i);
            if (fragment != null) {
                int startOffset = fragment.startOffset;
                int endOffset = fragment.endOffset;
                String styleName = fragment.styleName;
                writeFontStart(writer, styleName);
                for (int j = startOffset; j < endOffset; j++) {
                    columnCount = writeChar(writer, textBuffer.getChar(j), columnCount, false);
                }
                writeFontEnd(writer, styleName);
            }
        }
        writer.write("</pre>");
    }
}
