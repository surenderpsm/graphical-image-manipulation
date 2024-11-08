package model.command;

import model.Cache;
import model.Image;

/**
 * Implementation of red channel extraction from an RGB(A) image. Creates a new image containing
 * only the red channel data.
 */
class RedComponent extends AbstractColorComponent {

  /**
   * Constructs a new RedComponent extractor.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache        The cache containing stored images
   */
  public RedComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Alternative constructor for internal use.
   *
   * @param image     The source image
   * @param imageName The name for the resulting image
   * @param cache     The cache for storing the result
   */
  RedComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  /**
   * Extracts the red channel value for a specific pixel.
   *
   * @param x The x-coordinate of the pixel
   * @param y The y-coordinate of the pixel
   * @return An array containing [red, 0, 0] values
   */
  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[0] = currentImage.getRedChannelData()[x][y];
    return pixel;
  }

}
