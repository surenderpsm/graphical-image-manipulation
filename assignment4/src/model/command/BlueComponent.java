package model.command;

import model.Cache;
import model.Image;

/**
 * Implementation of blue channel extraction from an RGB(A) image. Creates a new image containing
 * only the blue channel data.
 */

class BlueComponent extends AbstractColorComponent {

  /**
   * Constructs a new BlueComponent extractor.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache        The cache containing stored images
   */

  public BlueComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Alternative constructor for internal use.
   *
   * @param image     The source image
   * @param imageName The name for the resulting image
   * @param cache     The cache for storing the result
   */

  BlueComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  /**
   * Extracts the blue channel value for a specific pixel.
   *
   * @param x The x-coordinate of the pixel
   * @param y The y-coordinate of the pixel
   * @return An array containing [0, 0, blue] values
   */
  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[2] = currentImage.getBlueChannelData()[x][y];
    return pixel;
  }

}
