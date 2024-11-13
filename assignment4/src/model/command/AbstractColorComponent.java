package model.command;

import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;

/**
 * An abstract base class for extracting color components from an image. This class provides the
 * framework for implementing various color component extraction operations (like getting red,
 * green, or blue channels).
 */
abstract class AbstractColorComponent extends Abstract2ArgCommand {

  /**
   * Constructs a new AbstractColorComponent with the specified arguments.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache        The cache containing stored images
   * @throws IllegalArgumentException if the number of arguments is not exactly 2
   */

  protected AbstractColorComponent(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(parseString(0));
    imageName = parseString(1);
  }

  /**
   * Constructs a new AbstractColorComponent with the specified image and name.
   *
   * @param image     The source image to process
   * @param imageName The name to store the result under
   * @param cache     The cache to store the result in
   */

  protected AbstractColorComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  /**
   * Processes the image by extracting the specified color component and storing the result in the
   * cache.
   */

  @Override
  public void execute() {
    // @todo deal with transparency
    int[][][] imageArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j] = getPixel(i, j);
      }
    }

    cache.set(imageName, new Image(imageArray));
  }

  /**
   * Gets the transformed pixel values for a specific position in the image.
   *
   * @param x The x-coordinate of the pixel
   * @param y The y-coordinate of the pixel
   * @return An array containing the transformed RGB values
   */

  protected abstract int[] getPixel(int x, int y);

  /**
   * method to add transparency.
   *
   * @param x The x-coordinate of the pixel
   * @param y The y-coordinate of the pixel
   * @return transparency.
   */
  private int addTransparency(int x, int y) {
    return currentImage.getTransparencyData()[x][y];
  }
}
