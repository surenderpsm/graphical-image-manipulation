package view.gui.frame;

import java.awt.image.BufferedImage;
import view.gui.GUI;

/**
 * The {@code UpdateObserver} interface provides methods to observe and handle updates
 * from the {@link GUI} facade. Implementations of this interface can update the
 * image, histogram, and other view-related components in the GUI.
 */
public interface UpdateObserver {

  /**
   * Updates the displayed image in the GUI.
   *
   * @param image the {@link BufferedImage} to display in the image area
   */
  void updateImage(BufferedImage image);

  /**
   * Updates the histogram display in the GUI.
   *
   * @param histogram the {@link BufferedImage} representing the histogram
   */
  void updateHistogram(BufferedImage histogram);

  /**
   * Displays an error message in the GUI.
   *
   * @param error the error message to be displayed
   */
  void displayError(String error);

  /**
   * Enables all GUI features, making them interactive.
   */
  void enableAllFeatures();

  /**
   * Sets the preview mode state in the GUI.
   *
   * @param enable {@code true} to enable preview mode; {@code false} otherwise
   */
  void setPreview(boolean enable);

  /**
   * Enables or disables channel settings in the GUI based on the state.
   *
   * @param enable {@code true} to enable channel settings; {@code false} otherwise
   */
  void setChannelSettings(boolean enable);
}
