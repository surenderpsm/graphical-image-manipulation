package view.gui;

import java.io.File;

/**
 * The {@code ComponentObserver} interface is implemented by classes that observe user interactions
 * with GUI components. Each JComponent in the UI is passed an instance of this observer, allowing
 * triggered actions to be captured and processed.
 */
public interface ComponentObserver {

  /**
   * Loads an image from the specified file.
   *
   * @param file the file containing the image to be loaded
   */
  void loadImage(File file);

  /**
   * Saves the current image to the specified file.
   *
   * @param file the file where the image should be saved
   */
  void saveImage(File file);

  /**
   * Adjusts the brightness of the loaded image.
   *
   * @param value the value by which to brighten or darken the image; positive for brightening,
   *              negative for darkening
   */
  void brighten(int value);

  /**
   * Applies a blur effect to the loaded image.
   */
  void blur();

  /**
   * Applies a sharpening effect to the loaded image.
   */
  void sharpen();

  /**
   * Flips the loaded image horizontally.
   */
  void hFlip();

  /**
   * Flips the loaded image vertically.
   */
  void vFlip();

  /**
   * Applies a sepia tone effect to the loaded image.
   */
  void sepia();

  /**
   * Converts the loaded image to grayscale.
   */
  void grayscale();

  /**
   * Visualizes the red component of the loaded image.
   */
  void redComponent();

  /**
   * Visualizes the blue component of the loaded image.
   */
  void blueComponent();

  /**
   * Visualizes the green component of the loaded image.
   */
  void greenComponent();

  /**
   * Compresses the loaded image using the specified ratio.
   *
   * @param ratio the compression ratio to apply
   */
  void compress(int ratio);

  /**
   * Applies color correction to the loaded image.
   */
  void colorCorrect();

  /**
   * Downscales the loaded image to the specified height and width.
   *
   * @param h the target height for the downscaled image
   * @param w the target width for the downscaled image
   */
  void downscale(int h, int w);

  /**
   * Adjusts the levels of the loaded image using specified black, mid, and white values.
   *
   * @param b the black level adjustment value
   * @param m the mid level adjustment value
   * @param w the white level adjustment value
   */
  void adjustLevels(int b, int m, int w);

  /**
   * Adjusts the split-level for preview mode.
   *
   * @param split the split-level between 1 and 100.
   */
  void splitLevel(int split);

  /**
   * Enables or disables preview mode.
   * In preview mode, users can view the effect of an operation before confirming or canceling it.
   * Certain commands may not support preview mode.
   *
   * @param preview {@code true} to enable preview mode; {@code false} to disable it.
   */
  void setPreviewMode(boolean preview);

  /**
   * Sets the confirmation status for an operation.
   * When confirmation is {@code true}, the operation is applied directly to the loaded image.
   * When {@code false}, the operation is aborted.
   *
   * @param confirm {@code true} if the operation is confirmed; {@code false} to cancel it.
   */
  void setConfirmation(boolean confirm);

  /**
   * Checks with the controller whether the application can quit.
   * The application may deny the quit request if there are unsaved changes.
   *
   * @return {@code true} if the application is allowed to quit; {@code false} otherwise.
   */
  boolean quit();

  /**
   * Resets the image display to the original loaded image.
   * This is used to undo temporary changes from color component visualizations.
   */
  void resetComponents();
}