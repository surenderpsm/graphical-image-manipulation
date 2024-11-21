package view.gui.frame.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import view.gui.frame.SubComponentBinder;

/**
 * FilterMenu is a custom menu component that provides image processing options such as
 * "Blur" and "Sharpen" within the GUI. It extends {@link JMenu} and adds specific
 * {@link JMenuItem} options for filtering operations, binding actions to the provided
 * {@link SubComponentBinder}.
 */
public class FilterMenu extends JMenu {

  /**
   * Constructs a new FilterMenu with options for "Blur" and "Sharpen".
   * The actions for these options are bound to the provided {@link SubComponentBinder},
   * enabling dynamic control over the availability and functionality of these menu items.
   *
   * @param binder the {@link SubComponentBinder} responsible for managing the menu items
   *               and binding actions to the view component listener
   */
  public FilterMenu(SubComponentBinder binder) {
    super("Filter");

    // Create and configure "Blur" menu item
    JMenuItem blur = new JMenuItem("Blur");
    binder.addToDisabledByDefault(blur);
    blur.addActionListener(e -> {
      binder.getViewComponentListener().blur();
    });

    // Create and configure "Sharpen" menu item
    JMenuItem sharpen = new JMenuItem("Sharpen");
    binder.addToDisabledByDefault(sharpen);
    sharpen.addActionListener(e -> {
      binder.getViewComponentListener().sharpen();
    });

    // Add blur and sharpen options to the menu
    add(blur);
    add(sharpen);
  }
}
