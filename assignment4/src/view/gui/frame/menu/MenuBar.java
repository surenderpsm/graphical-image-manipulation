package view.gui.frame.menu;

import javax.swing.JMenuBar;
import view.gui.frame.SubComponentBinder;

/**
 * The MenuBar class represents the menu bar of the GUI frame.
 * <p>
 * This menu bar includes multiple menus like File, Image, Color, and Filter,
 * providing options for the user to interact with the application.
 * It is initialized with the provided binder to pass necessary components to each menu.
 * </p>
 */
public class MenuBar extends JMenuBar {

  /**
   * Constructs a MenuBar and adds all the menu items to it.
   *
   * @param binder the {@link SubComponentBinder} that provides access to components
   *               like listeners which are passed into the menus.
   */
  public MenuBar(SubComponentBinder binder) {
    // Add menus to the menu bar
    add(new FileMenu(binder));   // Add File menu
    add(new ImageMenu(binder));  // Add Image menu
    add(new ColorMenu(binder));  // Add Color menu
    add(new FilterMenu(binder)); // Add Filter menu
  }
}
