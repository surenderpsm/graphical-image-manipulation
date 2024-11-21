package view.gui;

import java.io.File;

/**
 * Instances of this interface are observers of View components. Each JComponent in the UI is passed
 * this listener. The components notify this instance on any triggered actions.
 */
public interface ViewComponentListener {

  /**
   * Load image from a file.
   *
   * @param file A file to be loaded.
   */
  void loadImage(File file);

  /**
   * Save image using the file.
   *
   * @param file A file to be saved.
   */
  void saveImage(File file);

  /**
   * Brighten the loaded image.
   *
   * @param value integer to brighten or darken.
   */
  void brighten(int value);

  /**
   * Blur the loaded image.
   */
  void blur();

  /**
   * Sharpen the loaded image.
   */
  void sharpen();

  /**
   * Horizontal flip the image.
   */
  void hFlip();

  /**
   * Vertical flip the image.
   */
  void vFlip();

  /**
   * Sepia on the loaded image.
   */
  void sepia();

  /**
   * Grayscale on the loaded image.
   */
  void grayscale();

  /**
   * Visualize the red component.
   */
  void redComponent();

  /**
   * Visualize the blue component.
   */
  void blueComponent();

  /**
   * Visualize the green component.
   */
  void greenComponent();

  /**
   * Compress the image with the provided ratio.
   *
   * @param ratio the compression factor.
   */
  void compress(int ratio);

  /**
   * Color correct the loaded image.
   */
  void colorCorrect();

  /**
   * Downscale an image based on the input height and width.
   *
   * @param h input height
   * @param w input width
   */
  void downscale(int h, int w);

  /**
   * Adjust levels using black, mid and white values.
   *
   * @param b black level
   * @param m mid level
   * @param w white level
   */
  void adjustLevels(int b, int m, int w);

  /**
   * Adjust the split level during preview mode.
   *
   * @param split the split-level between 1 and 100.
   */
  void splitLevel(int split);

  /**
   * Turn on or off previewMode. It is deactivated by use of the "confirm" and the "cancel" buttons.
   * Running a command activates preview mode. Preview mode allows the use of the split slider.
   * Preview mode need not be activated for commands that don't support the split operation.
   *
   * @param preview true if preview mode is on, false otherwise.
   */
  void setPreviewMode(boolean preview);

  /**
   * Confirmation status is set with this method. Confirmation is true when the "confirm" button is
   * clicked. A true confirm status applies the operation directly on the loaded image instead of
   * the preview version. A false confirmation status is applied when the "cancel" button is
   * clicked. The operation is aborted when the confirmation status is false.
   *
   * @param confirm true or false.
   */
  void setConfirmation(boolean confirm);

  /**
   * Checks with the controller if the application is allowed to quit.
   *
   * @return true if allowed to quit, otherwise false
   */
  boolean quit();

  /**
   * Resets the image to the loaded image after a color-component visualization is applied.
   */
  void resetComponents();

}
