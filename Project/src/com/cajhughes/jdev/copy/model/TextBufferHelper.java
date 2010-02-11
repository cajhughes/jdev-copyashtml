package com.cajhughes.jdev.copy.model;

import java.io.IOException;
import java.io.StringWriter;
import oracle.javatools.buffer.LineMap;
import oracle.javatools.buffer.TextBuffer;

/*
 * @author Chris Hughes
 *
 * This class provides helper methods for handling TextBuffer content.
 *
 * No manipulation of the TextBuffer passed into the constructor should be
 * performed via methods separate from this class, otherwise incorrect results
 * may be returned for the <i>getLine</i> method.
 */
public class TextBufferHelper
{
    private StringBuffer stringBuffer = null;
    private TextBuffer textBuffer = null;

    public TextBufferHelper(final TextBuffer buffer) {
        this.textBuffer = buffer;
        this.stringBuffer = getText();
    }

    /*
     * Returns a StringBuffer which contains the characters from start to
     * end of the specified line index.  The buffer returned does not contain
     * any trailing carriage return or line feed character.
     */
    public StringBuffer getLine(final int line) {
        StringBuffer result = null;
        if (textBuffer != null && stringBuffer != null) {
            LineMap lineMap = textBuffer.getLineMap();
            if (lineMap != null) {
                int lineCount = lineMap.getLineCount();
                if ((line >= 0) && (line < lineCount)) {
                    int lineStartOffset = lineMap.getLineStartOffset(line);
                    int lineEndOffset = lineMap.getLineEndOffset(line);
                    if (lineEndOffset >= lineStartOffset) {
                        result = new StringBuffer();
                        if (isLastLine(line)) {
                            result.append(stringBuffer.substring(lineStartOffset,
                                                                 lineEndOffset));
                        }
                        else if (lineEndOffset > lineStartOffset) {
                            result.append(stringBuffer.substring(lineStartOffset,
                                                                 lineEndOffset -
                                                                 1));
                        }
                    }
                }
            }
        }
        return result;
    }

    /*
     * Returns a StringBuffer that matches the contents of the TextBuffer.
     *
     * Note that we explicitly call "setEOLType" to ensure that the
     * StringBuffer is populated with the same character offsets as the
     * underlying TextBuffer.
     */
    public StringBuffer getText() {
        StringBuffer result = null;
        if (textBuffer != null) {
            StringWriter writer = new StringWriter();
            try {
                /*
                 * Store the current end-of-line string
                 */
                String eolType = textBuffer.getEOLType();

                /*
                 * Explicitly set the end-of-line string while we write the
                 * contents of the TextBuffer into our writer
                 */
                textBuffer.setEOLType(TextBuffer.EOL_LF);
                textBuffer.write(writer, false);
                writer.flush();
                result = writer.getBuffer();

                /*
                 * Restore the end-of-line string
                 */
                textBuffer.setEOLType(eolType);
            }
            catch (IOException ioe) {
                result = null;
            }
        }
        return result;
    }

    /*
     * Returns a boolean that indicates whether the index references the last
     * line in the TextBuffer.
     *
     * The calculation accounts for the fact that indices in the LineMap
     * class are zero-based, as compared to the one-based value returned
     * from getLineCount().
     */
    public boolean isLastLine(final int index) {
        boolean isLast = false;
        if (textBuffer != null) {
            LineMap lineMap = textBuffer.getLineMap();
            if (lineMap != null) {
                int lineCount = lineMap.getLineCount();
                isLast = ((index + 1) == lineCount);
            }
        }
        return isLast;
    }

    /*
     * Returns an UndoableEdit that contains the undo information for the
     * removal of the last <i>count</i> characters (not including any
     * terminating end-of-line character) from line <i>line</i>.
     */
    public void removeTrailingChars(final int line, final int count) {
        if (textBuffer != null) {
            LineMap lineMap = textBuffer.getLineMap();
            int lineCount = lineMap.getLineCount();
            if (line < lineCount) {
                int lineEndOffset = lineMap.getLineEndOffset(line);
                int startPosition;
                if (isLastLine(line)) {
                    startPosition = (lineEndOffset - count);
                }
                else {
                    startPosition = (lineEndOffset - count - 1);
                }
                textBuffer.remove(startPosition, count);
                stringBuffer.delete(startPosition,
                                    (startPosition + count + 1));
            }
        }
    }
}
