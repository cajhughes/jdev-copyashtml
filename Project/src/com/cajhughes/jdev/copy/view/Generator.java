package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.model.CopyPreferences;
import java.io.IOException;
import java.io.Writer;
import oracle.javatools.editor.BasicDocument;
import oracle.javatools.editor.EditorProperties;
import oracle.javatools.editor.language.BaseStyle;
import oracle.javatools.editor.language.StyleRegistry;

public abstract class Generator {
    protected BasicDocument document = null;
    protected StyleRegistry registry = null;

    public Generator(final BasicDocument document) {
        this.document = document;
        EditorProperties properties = EditorProperties.getProperties();
        if (properties != null) {
            registry = properties.getStyleRegistry();
        }
    }
    
    protected abstract void generate(final Writer writer, final CopyPreferences prefs) throws IOException;

    protected BaseStyle getStyle(final String styleName) {
        BaseStyle baseStyle = null;
        if (styleName != null) {
            baseStyle = registry.lookupStyle(styleName);
        }
        return baseStyle;
    }
}
