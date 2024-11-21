package view.gui;

import controller.viewhandler.ViewHandler;
import java.awt.image.BufferedImage;

public interface GUIHandlingObject {

  void addObserver(GUIViewListener observer);

  void updateImage(BufferedImage image);

  void updateHistogram(BufferedImage histogram);

  void displayError(String message);

  int getSplit();

  void enableAllFeatures();

  /**
   * This lets the calling object if the GUI is in preview mode.
   *
   * @return
   */
  boolean isPreviewMode();

  /**
   * @return
   */
  boolean isConfirmed();
}
