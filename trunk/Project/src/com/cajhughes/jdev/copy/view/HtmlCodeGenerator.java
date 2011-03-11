package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.model.CopyPreferences;
import com.cajhughes.jdev.copy.model.TextBufferHelper;
import java.io.IOException;
import java.io.Writer;
import oracle.javatools.buffer.LineMap;
import oracle.javatools.buffer.TextBuffer;
import oracle.javatools.editor.BasicDocument;

public class HtmlCodeGenerator extends HtmlGenerator {
    
    public HtmlCodeGenerator(final BasicDocument document) {
        super(document);
    }

    protected void generate(final Writer writer, final CopyPreferences prefs) throws IOException {
        TextBuffer textBuffer = document.getTextBuffer();
        LineMap lineMap = textBuffer.getLineMap();
        int lineCount = lineMap.getLineCount();
        TextBufferHelper helper = new TextBufferHelper(textBuffer);
        writer.write("<code>");
        for (int i = 0; i < lineCount; i++) {
            int columnCount = 0;
            StringBuffer line = helper.getLine(i);
            int lineLength = line.length();
            for (int j = 0; j < lineLength; j++) {
                char c = line.charAt(j);
                switch (c) {
                    case 10:
                    case 13:
                        break;
                    case 32:
                        writer.write("&nbsp;");
                        columnCount++;
                        break;
                    default:
                        columnCount = writeChar(writer, c, columnCount, true);
                        break;
                }
            }
            if (!helper.isLastLine(i)) {
                writer.write("<br/>\n");
            }
        }
        writer.write("</code>");
    }
}
