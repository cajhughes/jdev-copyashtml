package chughes.jdev.copy.view;

import chughes.jdev.copy.model.CopyPreferences;
import java.io.IOException;
import java.io.Writer;
import javax.swing.text.BadLocationException;
import oracle.ide.Ide;
import oracle.ide.ceditor.options.PrintingHTMLOptions;
import oracle.javatools.editor.BasicDocument;
import oracle.javatools.editor.print.DocumentToHTMLGenerator;
import oracle.javatools.editor.print.HTMLGeneratorOptions;

/*
 * @author Chris Hughes
 *
 * This class provides a method for formatting the document fragment passed
 * into the constructor in the normal way for that file type as HTML.
 *
 * It accepts a format parameter which should match one of the CopyPreferences
 * options:
 *
 * (a) CopyPreferences.CODE will produce partial HTML output, which includes
 *     the selected text wrapped in a <CODE> tag
 * (b) CopyPreferences.PRE will produce partial HTML output, which includes
 *     the selected text and coloring wrapped in a <PRE> tag
 * (c) CopyPreferences.FULL will produce complete HTML output, which adheres
 *     to the options specified in the Printing HTML preference
 */
public class HTMLFormatter
{
    private String filename = null;
    private String text = null;

    public HTMLFormatter (final String filename, final String text)
    {
        this.filename = filename;
        this.text = text;
    }

    /*
     * This method formats the document fragment and places the result in
     * the specified Writer.
     */
    public void format (final Writer writer, final int format)
        throws BadLocationException, IOException
    {
        BasicDocument document = new BasicDocument(filename);
        document.insertString(0, text, null);
        if (format == CopyPreferences.CODE)
        {
            HTMLGenerator generator = new HTMLGenerator(document);
            generator.generateCode(writer);
        }
        else if (format == CopyPreferences.PRE)
        {
            HTMLGenerator generator = new HTMLGenerator(document);
            generator.generatePre(writer);
        }
        else if (format == CopyPreferences.FULL)
        {
            DocumentToHTMLGenerator generator =
                new DocumentToHTMLGenerator(document, null);
            generator.generateHTML(writer, getOptions());
        }
    }

    public static HTMLGeneratorOptions getOptions ()
    {
        HTMLGeneratorOptions result = new HTMLGeneratorOptions();
        PrintingHTMLOptions printOptions =
            (PrintingHTMLOptions)
                Ide.getSettings().getData(PrintingHTMLOptions.KEY_SETTINGS);
        if (printOptions != null)
        {
            result.setCharsetEncoding(printOptions.getCharsetEncoding());
            result.setFontFamily(printOptions.getFontFamily());
            result.setFontSize(printOptions.getFontSize());
            result.setPrintColors(printOptions.getPrintColors());
            result.setPrintFileHeader(printOptions.getPrintFileHeader());
            result.setPrintFontStyles(printOptions.getPrintFontStyles());
            result.setPrintLineNumbers(printOptions.getPrintLineNumbers());
            result.setPrintTimeStamp(printOptions.getPrintTimeStamp());
            result.setUseAnchors(printOptions.getUseAnchors());
            result.setUseFontInfo(printOptions.getUseFontInfo());
            result.setWrapBehavior(printOptions.getWrapBehavior());
            result.setWrapColumn(printOptions.getWrapColumn());
            result.setWrapSymbol(printOptions.getWrapSymbol());
        }
        return result;
    }
}