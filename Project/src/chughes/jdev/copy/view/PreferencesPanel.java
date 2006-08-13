package chughes.jdev.copy.view;

import chughes.jdev.copy.model.CopyPreferences;
import chughes.jdev.copy.view.resource.CopyResourceKeys;
import chughes.jdev.copy.view.resource.CopyResourceUtil;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import oracle.ide.panels.DefaultTraversablePanel;
import oracle.ide.panels.TraversableContext;
import oracle.ide.panels.TraversalException;
import oracle.ide.util.ResourceUtils;

/*
 * @author Chris Hughes
 *
 * This class defines the panel that will be displayed, on the right hand
 * side of the Preferences dialog, when selecting the Copy As HTML option
 * in the left hand side of the dialog.
 *
 * It reads the CopyPreferences class on entry to determine the which
 * radio button to select, and sets the CopyPreferences class on exit.
 */
public final class PreferencesPanel extends DefaultTraversablePanel
{
    private final JLabel       heading = new JLabel();
    private final JRadioButton codeSnippet = new JRadioButton();
    private final JRadioButton preSnippet = new JRadioButton();
    private final JRadioButton fullSnippet = new JRadioButton();

    public PreferencesPanel()
    {
        initialiseResources();
        layoutControls();
        initialiseControls();
    }

    protected void initialiseResources ()
    {
        ResourceUtils.resLabel(
            heading,
            heading,
            CopyResourceUtil.getString(CopyResourceKeys.HEADING));
        ResourceUtils.resButton(
            codeSnippet,
            CopyResourceUtil.getString(
                CopyResourceKeys.CODE_OPTION));
        ResourceUtils.resButton(
            preSnippet,
            CopyResourceUtil.getString(CopyResourceKeys.PRE_OPTION));
        ResourceUtils.resButton(
            fullSnippet,
            CopyResourceUtil.getString(
                CopyResourceKeys.FULL_OPTION));
    }

    protected void initialiseControls ()
    {
        ButtonGroup group = new ButtonGroup();
        group.add(codeSnippet);
        group.add(preSnippet);
        group.add(fullSnippet);
    }

    protected void layoutControls ()
    {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
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
        add(fullSnippet, c);
        c.gridy++;
        c.weighty = 1.0d;
        add(new JPanel(), c);
    }

    public void onEntry (final TraversableContext context)
    {
        CopyPreferences prefs =
            (CopyPreferences)context.find(CopyPreferences.KEY);
        if (prefs != null)
        {
            codeSnippet.setSelected(
                prefs.getCopyFormat() == CopyPreferences.CODE);
            preSnippet.setSelected(
                prefs.getCopyFormat() == CopyPreferences.PRE);
            fullSnippet.setSelected(
                prefs.getCopyFormat() == CopyPreferences.FULL);
        }
    }

    public void onExit (final TraversableContext context)
        throws TraversalException
    {
        CopyPreferences prefs =
            (CopyPreferences)context.find(CopyPreferences.KEY);
        if (prefs != null)
        {
            if (codeSnippet.isSelected())
            {
                prefs.setCopyFormat(CopyPreferences.CODE);
            }
            else if (preSnippet.isSelected())
            {
                prefs.setCopyFormat(CopyPreferences.PRE);
            }
            else if (fullSnippet.isSelected())
            {
                prefs.setCopyFormat(CopyPreferences.FULL);
            }
        }
    }
}