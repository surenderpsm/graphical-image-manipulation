package view.gui;

import java.io.File;

/**
 * Instances of this interface are observers of View components. Each JComponent in the UI is passed
 * this listener. The components notify this instance on any triggered actions.
 */
public interface ViewComponentListener {

  void loadImage(File file);

  void saveImage(File file);

  void brighten(int value);

  void blur();

  void sharpen();

  void hFlip();

  void vFlip();

  void sepia();

  void grayscale();

  void redComponent();

  void blueComponent();

  void greenComponent();

  void compress(int ratio);

  void colorCorrect();

  void downscale(int h, int w);

  void adjustLevels(int b, int m, int w);

  void splitLevel(int split);

  void setPreviewMode(boolean preview);

  void setConfirmation(boolean confirm);

  boolean quit();

  void resetComponents();

}
