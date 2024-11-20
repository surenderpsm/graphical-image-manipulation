package view.gui;

import java.awt.image.BufferedImage;

/**
 * This interface is used to observe for updates from the GUI Facade class {@link GUI}.
 */
public interface UpdateObserver {

  /**
   * Invokes if the calling object intends to update the image area in the DefaultFrame.
   *
   * @param image BufferedImage
   */
  void updateImage(BufferedImage image);

  /**
   * Invoked if the calling object intends to update the histogram area in the DefaultFrame.
   *
   * @param histogram BufferedImage of histogram.
   */
  void updateHistogram(BufferedImage histogram);

  void displayError(String error);

  void enableAllFeatures();
}
