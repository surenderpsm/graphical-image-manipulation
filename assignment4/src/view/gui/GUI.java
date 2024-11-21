package view.gui;

import java.awt.image.BufferedImage;

public interface GUI {

  void addObserver(GUIObserver observer);

  void updateImage(BufferedImage image);

  void updateHistogram(BufferedImage histogram);

  void displayError(String message);

  int getSplit();

  void enableAllFeatures();

  /**
   * This lets the calling object if the GUIImpl is in preview mode.
   *
   * @return
   */
  boolean isPreviewMode();

  /**
   * @return
   */
  boolean isConfirmed();
}
