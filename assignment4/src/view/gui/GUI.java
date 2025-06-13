package view.gui;

import java.awt.image.BufferedImage;

/**
 * The GUI interface defines the methods required for a graphical user interface implementation. It
 * provides functionality for updating images and histograms, managing observers, and controlling
 * the state of the GUI, including preview and confirmation modes.
 * <br>
 * A {@link GUIObserver} can observe the changes i.e. the inputs made by the user on the GUI.
 */
public interface GUI {

  /**
   * Registers an observer to listen for events or state changes in the GUI.
   *
   * @param observer the observer to be added
   */
  void addObserver(GUIObserver observer);

  /**
   * Updates the currently displayed image in the GUI.
   *
   * @param image the image to be displayed
   */
  void updateImage(BufferedImage image);

  /**
   * Updates the displayed histogram representation in the GUI.
   *
   * @param histogram the histogram image to be displayed
   */
  void updateHistogram(BufferedImage histogram);

  /**
   * Displays an error message in the GUI.
   *
   * @param message the error message to be displayed
   */
  void displayError(String message);

  /**
   * Retrieves the percentage of split specified by the user.
   *
   * @return the percentage of split as an integer
   */
  int getSplit();

  /**
   * Enables all features (menu options) in the GUI, making them available for interaction.
   */
  void enableAllFeatures();

  /**
   * Indicates whether the GUI is currently in preview mode.
   *
   * @return {@code true} if the GUI is in preview mode; {@code false} otherwise
   */
  boolean isPreviewMode();

  /**
   * Indicates whether the user has confirmed their action or choice in the GUI.
   *
   * @return {@code true} if the action is confirmed; {@code false} otherwise
   */
  boolean isConfirmed();
}
