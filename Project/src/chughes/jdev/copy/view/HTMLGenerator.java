package chughes.jdev.copy.view;

import chughes.jdev.copy.model.TextBufferHelper;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.Writer;
import oracle.javatools.buffer.LineMap;
import oracle.javatools.buffer.TextBuffer;
import oracle.javatools.editor.BasicDocument;
import oracle.javatools.editor.EditorProperties;
import oracle.javatools.editor.Utilities;
import oracle.javatools.editor.language.BaseStyle;
import oracle.javatools.editor.language.DocumentRenderer;
import oracle.javatools.editor.language.StyleRegistry;
import oracle.javatools.editor.language.StyledFragment;
import oracle.javatools.editor.language.StyledFragmentsList;

/*
 * @author Chris Hughes
 *
 * This class constructs partial HTML output for the Document passed in.
 * The intention behind the production of this partial output being that
 * this can then be pasted into a separate HTML document (such as a weblog)
 * easily.
 */
public class HTMLGenerator
{
	protected static final String DEFAULT_STYLE = "base-plain-style";

    private BasicDocument document = null;
	private StyleRegistry registry = null;
    private int tabSize = 0;

	/*
	 * The constructor stores a local copy of the document, and also reads
	 * the editor settings for style & tab size, for use when creating the
	 * partial output.
	 */
    public HTMLGenerator (final BasicDocument document)
    {
        this.document = document;
        EditorProperties properties = EditorProperties.getProperties();
        if (properties != null)
        {
            registry = properties.getStyleRegistry();
            tabSize =
                properties.getIntegerProperty(
                    EditorProperties.PROPERTY_TAB_SIZE);
        }
    }

	/*
	 * Generates the partial output associated with the CopyPreferences.CODE
	 * option in the preferences.
	 */
    public void generateCode (final Writer writer) throws IOException
    {
        TextBuffer textBuffer = document.getTextBuffer();
        LineMap lineMap = textBuffer.getLineMap();
        int lineCount = lineMap.getLineCount();
        TextBufferHelper helper = new TextBufferHelper(textBuffer);
        writer.write("<code>\n");
        for (int i = 0; i < lineCount; i++)
        {
            int columnCount = 0;
            StringBuffer line = helper.getLine(i);
            int lineLength = line.length();
            for (int j = 0; j < lineLength; j++)
            {
                char c = line.charAt(j);
                switch(c)
                {
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
            if (!helper.isLastLine(i))
            {
                writer.write("<br/>\n");
            }
        }
        writer.write("</code>\n");
    }

	/*
	 * Generates the partial output associated with the CopyPreferences.PRE
	 * option in the preferences.
	 */
    public void generatePre (final Writer writer) throws IOException
    {
        TextBuffer textBuffer = document.getTextBuffer();
        DocumentRenderer renderer = document.getDocumentRenderer();
        LineMap lineMap = textBuffer.getLineMap();
        int lineCount = lineMap.getLineCount();
        StyledFragmentsList fragmentList = renderer.renderLines(0, lineCount-1);
        int numFragments = fragmentList.size();
        writer.write("<pre>\n");
        int columnCount = 0;
        for (int i = 0; i < numFragments; i++)
        {
            StyledFragment fragment = fragmentList.get(i);
            if (fragment != null)
            {
                int startOffset = fragment.startOffset;
                int endOffset = fragment.endOffset;
                String styleName = fragment.styleName;
				writeFontStart(writer, styleName);
                for (int j = startOffset; j < endOffset; j++)
                {
					columnCount = writeChar(writer,
                                            textBuffer.getChar(j),
                                            columnCount,
                                            false);
                }
				writeFontEnd(writer, styleName);
            }
        }
        writer.write("</pre>\n");
    }

	/*
	 * To avoid bloating the partial HTML that we produce, we only want to
	 * specify font information if the style is something other than
	 * the default style.
	 */
	protected BaseStyle getNonPlainStyle (final String styleName)
	{
		BaseStyle baseStyle = null;
		if (styleName != null && !styleName.equals(DEFAULT_STYLE))
		{
			baseStyle = registry.lookupStyle(styleName);
		}
		return baseStyle;
	}

	/*
	 * Escape the character for HTML:
	 *
	 * (a) Include the appropriate number of spaces instead of a TAB
	 * (b) Reset the column if we write a CR or LF
	 * (c) " -> &quot;
	 * (d) & -> &amp;
	 * (e) < -> &lt;
	 * (f) > -> &gt;
	 */
    protected int writeChar (final Writer  writer,
                             final char    c,
                             final int     column,
                             final boolean escapeSpace)
        throws IOException
    {
        int result = column;
        switch(c)
        {
            case 9:
                int nextStop = Utilities.getNextTabStop(tabSize, column);
                int spaces = nextStop - column;
                for (int s = 0; s < spaces; s++)
                {
                    if (escapeSpace)
                    {
                        writer.write("&nbsp;");
                    }
                    else
                    {
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

    protected void writeColor(final Writer writer, final Color color)
        throws IOException
    {
        int value = color.getRGB() & 0xffffff;
        String hexValue = Integer.toHexString(value);
        writer.write("#");
        int hexLen = hexValue.length();
        int paddingToWrite = 6 - hexLen;
        for(int i = 0; i < paddingToWrite; i++)
        {
            writer.write(48);
        }
        writer.write(hexValue);
    }

	protected void writeFontStart (final Writer writer, final String styleName)
		throws IOException
	{
		if (registry != null)
		{
			BaseStyle style = getNonPlainStyle(styleName);
			if (style != null)
			{
				writer.write("<font color=\"");
				writeColor(writer, style.getForegroundColor());
				writer.write("\">");
				if ((style.getFontStyle() & Font.BOLD) > 0)
				{
					writer.write("<b>");
				}
				if ((style.getFontStyle() & Font.ITALIC) > 0)
				{
					writer.write("<i>");
				}
			}
		}
	}

	protected void writeFontEnd (final Writer writer, final String styleName)
		throws IOException
	{
		if (registry != null)
		{
			BaseStyle style = getNonPlainStyle(styleName);
			if (style != null)
			{
				if ((style.getFontStyle() & Font.ITALIC) > 0)
				{
					writer.write("</i>");
				}
				if ((style.getFontStyle() & Font.BOLD) > 0)
				{
					writer.write("</b>");
				}
				writer.write("</font>");
			}
		}
	}
}