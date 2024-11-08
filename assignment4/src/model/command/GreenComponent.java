package model.command;

import model.Cache;
import model.Image;

/**
 * Implementation of green channel extraction from an RGB(A) image.
 * Creates a new image containing only the green channel data, with
 * red and blue channels set to zero.
 */

class GreenComponent extends AbstractColorComponent {

  /**
   * Constructs a new GreenComponent extractor.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache The cache containing stored images
   */

  public GreenComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Alternative constructor for internal use.
   *
   * @param image The source image
   * @param imageName The name for the resulting image
   * @param cache The cache for storing the result
   */

  GreenComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  /**
   * Extracts the green channel value for a specific pixel.
   *
   * @param x The x-coordinate of the pixel
   * @param y The y-coordinate of the pixel
   * @return An array containing [0, green, 0] values
   */
  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[1] = currentImage.getGreenChannelData()[x][y];
    return pixel;
  }

}
