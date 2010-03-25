package com.cajhughes.jdev.copy.view;

import com.cajhughes.jdev.copy.model.CopyPreferences;
import com.cajhughes.jdev.copy.view.resource.CopyResourceUtil;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import oracle.ide.ceditor.options.FontManager;
import oracle.ide.ceditor.options.OptionsArb;
import oracle.ide.controls.ItemSelectableTracker;
import oracle.ide.controls.NonNullableComboBoxModel;
import oracle.ide.panels.DefaultTraversablePanel;
import oracle.ide.panels.TraversableContext;
import oracle.ide.panels.TraversalException;
import oracle.ide.util.ResourceUtils;

/**
 * This class extends the DefaultTraversablePanel, so that the JPanel for the
 * preferences associated with the CopyAsHtml action can be traversed within
 * the Preferences dialog.
 *
 * @author Chris Hughes
 */
public final class CopyPreferencesPanel extends DefaultTraversablePanel {
    private final JLabel heading = new JLabel();
    private final JRadioButton codeSnippet = new JRadioButton();
    private final JRadioButton preSnippet = new JRadioButton();
    private final JRadioButton codeMarkupSnippet = new JRadioButton();
    private final JRadioButton fullSnippet = new JRadioButton();
    private final JRadioButton rtfSnippet = new JRadioButton();
    private final JLabel familyLabel = new JLabel();
    private final JComboBox fontFamily = new JComboBox();
    private final JLabel sizeLabel = new JLabel();
    private final JComboBox fontSize = new JComboBox();

    public CopyPreferencesPanel() {
        initialiseResources();
        layoutControls();
        initialiseControls();
    }

    protected void initialiseResources() {
        ResourceUtils.resLabel(heading, heading, CopyResourceUtil.getString("CopyPanelHeading"));
        ResourceUtils.resButton(codeSnippet, CopyResourceUtil.getString("CopyPanelCodeOption"));
        ResourceUtils.resButton(preSnippet, CopyResourceUtil.getString("CopyPanelPreOption"));
        ResourceUtils.resButton(codeMarkupSnippet, CopyResourceUtil.getString("CopyPanelCodeMarkupOption"));
        ResourceUtils.resButton(fullSnippet, CopyResourceUtil.getString("CopyPanelFullOption"));
        ResourceUtils.resButton(rtfSnippet, CopyResourceUtil.getString("CopyPanelRTFOption"));
        ResourceUtils.resLabel(familyLabel, familyLabel, OptionsArb.getString(OptionsArb.LABEL_PRINT_HTML_FONT));
        ResourceUtils.resLabel(sizeLabel, sizeLabel, OptionsArb.getString(OptionsArb.LABEL_PRINT_HTML_SIZE));
    }

    protected void layoutControls() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.anchor = 17;
        c.weightx = 1.0d;
        c.weighty = 0.0d;
        c.insets = new Insets(0, 0, 5, 0);
        add(heading, c);
        c.gridy++;
        c.gridwidth = 1;
        c.weightx = 0.0d;
        c.insets = new Insets(0, 24, 5, 5);
        add(codeSnippet, c);
        c.gridy++;
        add(preSnippet, c);
        c.gridy++;
        add(codeMarkupSnippet, c);
        c.gridy++;
        add(fullSnippet, c);
        c.gridy++;
        add(rtfSnippet, c);
        c.gridy++;
        c.insets = new Insets(0, 48, 5, 5);
        add(familyLabel, c);
        c.insets = new Insets(0, 120, 5, 5);
        add(fontFamily, c);
        c.gridy++;
        c.insets = new Insets(0, 48, 5, 5);
        add(sizeLabel, c);
        c.insets = new Insets(0, 120, 5, 5);
        add(fontSize, c);
        c.gridy++;
        c.weighty = 1.0d;
        add(new JPanel(), c);
    }

    protected void initialiseControls() {
        ButtonGroup group = new ButtonGroup();
        group.add(codeSnippet);
        group.add(preSnippet);
        group.add(codeMarkupSnippet);
        group.add(fullSnippet);
        group.add(rtfSnippet);
        FontManager fontManager = FontManager.getInstance();
        String[] families = fontManager.getAllFontFamilies();
        NonNullableComboBoxModel fontModel = new NonNullableComboBoxModel(families);
        fontFamily.setModel(fontModel);
        fontFamily.setEditable(false);
        fontFamily.setSelectedItem(Font.DIALOG_INPUT);
        Integer[] sizes = { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 };
        NonNullableComboBoxModel sizeModel = new NonNullableComboBoxModel(sizes);
        fontSize.setModel(sizeModel);
        fontSize.setEditable(false);
        fontSize.setSelectedItem(sizes[0]);
        Component[] dependents = { familyLabel, fontFamily, sizeLabel, fontSize };
        new ItemSelectableTracker(rtfSnippet, dependents);
    }

    @Override
    public void onEntry(final TraversableContext context) {
        CopyPreferences prefs = (CopyPreferences)context.find(CopyPreferences.KEY);
        if (prefs != null) {
            // Load the font information first, so that we disable/enable when we set rtfSnippet
            fontFamily.setSelectedItem(prefs.getFontFamily());
            fontSize.setSelectedItem(prefs.getFontSize());

            codeSnippet.setSelected(prefs.getCopyFormat() == CopyPreferences.CODE);
            preSnippet.setSelected(prefs.getCopyFormat() == CopyPreferences.PRE);
            codeMarkupSnippet.setSelected(prefs.getCopyFormat() == CopyPreferences.CODEMARKUP);
            fullSnippet.setSelected(prefs.getCopyFormat() == CopyPreferences.FULL);
            rtfSnippet.setSelected(prefs.getCopyFormat() == CopyPreferences.RTF);
        }
    }

    @Override
    public void onExit(final TraversableContext context) throws TraversalException {
        CopyPreferences prefs = (CopyPreferences)context.find(CopyPreferences.KEY);
        if (prefs != null) {
            if (codeSnippet.isSelected()) {
                prefs.setCopyFormat(CopyPreferences.CODE);
            }
            else if (preSnippet.isSelected()) {
                prefs.setCopyFormat(CopyPreferences.PRE);
            }
            else if (codeMarkupSnippet.isSelected()) {
                prefs.setCopyFormat(CopyPreferences.CODEMARKUP);
            }
            else if (fullSnippet.isSelected()) {
                prefs.setCopyFormat(CopyPreferences.FULL);
            }
            else if (rtfSnippet.isSelected()) {
                prefs.setCopyFormat(CopyPreferences.RTF);
            }
            prefs.setFontFamily((String)fontFamily.getSelectedItem());
            prefs.setFontSize(((Integer)fontSize.getSelectedItem()).intValue());
        }
    }
}
