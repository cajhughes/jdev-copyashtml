package chughes.jdev.copy.view;

import chughes.jdev.copy.view.resource.CopyResourceKeys;
import chughes.jdev.copy.view.resource.CopyResourceUtil;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.MenuConstants;
import oracle.ide.config.IdeSettings;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuManager;
import oracle.ide.panels.Navigable;
import oracle.ide.util.GraphicsUtils;

/*
 * @author Chris Hughes
 *
 * This class centralises the initialisation work required for the user
 * interface parts of the extension:
 *
 * (a) Register our panel as part of the Preferences dialog
 * (b) Associate our extension with a menu item
 */
public class UserInterface
{
    protected static final JMenuItem getCopyMenuItem
        (final Controller controller,
         final int        commandId)
    {
        JMenuItem menuItem = null;
		String menuLabel =
			CopyResourceUtil.getString(CopyResourceKeys.COPY_MENU_LABEL);
        ImageIcon icon =
			new ImageIcon(
                GraphicsUtils.loadFromResource(
                    CopyResourceUtil.getString(CopyResourceKeys.COPY_MENU_ICON),
                    controller.getClass()));
        IdeAction action = IdeAction.get(commandId,
                                         null,
                                         menuLabel,
                                         null,
                                         null,
                                         icon,
                                         null,
                                         true);
        if (action != null)
        {
            action.addController(controller);
            menuItem = Ide.getMenubar().createMenuItem(action);
        }
        return menuItem;
    }

    public static void initialize (final Controller controller,
                                   final int        commandId)
    {
        IdeSettings.registerUI(
            new Navigable(
                CopyResourceUtil.getString(CopyResourceKeys.COPY_ADDIN_NAME),
                PreferencesPanel.class));
        Ide.getMenubar().add(
            getCopyMenuItem(controller, commandId),
            MenuManager.getJMenu(IdeMainWindow.MENU_EDIT),
            MenuConstants.SECTION_EDIT_CUT_COPY_PASTE);
    }
}