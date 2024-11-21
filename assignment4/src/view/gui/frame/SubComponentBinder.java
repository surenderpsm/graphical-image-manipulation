package view.gui.frame;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import view.gui.ComponentObserver;

/**
 * Interface for binding components to a main GUI frame.
 * <br>
 * This interface defines methods for enabling or disabling specific components, retrieving the
 * main frame, and managing the view component listener.
 */
public interface SubComponentBinder {

  /**
   * Adds a menu item to the list of components that are disabled by default.
   *
   * @param item the menu item to disable by default.
   */
  void addToDisabledByDefault(JMenuItem item);

  /**
   * Retrieves the view component listener, which handles user interactions and updates.
   *
   * @return the ComponentObserver instance for handling interactions.
   */
  ComponentObserver getViewComponentListener();

  /**
   * Retrieves the main display frame of the application.
   *
   * @return the JFrame representing the main display frame.
   */
  JFrame getDisplayFrame();
}
