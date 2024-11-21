package view.gui.frame;

import java.awt.image.BufferedImage;
import view.gui.GUIImpl;

/**
 * This interface is used to observe for updates from the GUIImpl Facade class {@link GUIImpl}.
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

  void setPreview(boolean enable);

  void setChannelSettings(boolean enable);
}
