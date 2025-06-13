package model.command;

import model.Cache;
import utils.arguments.ArgumentWrapper;

/**
 * Adjusts the brightness of an image by adding a constant value to each color channel. Handles both
 * grayscale and color images appropriately.
 */
class Brighten extends SimpleImageProcessor {

  private final int value;

  /**
   * A public constructor that input the arguments as string.
   *
   * @param rawArguments Raw Arguments string.
   */
  public Brighten(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }
    value = parseInt(0);
    currentImage = cache.get(parseString(1));
    imageName = parseString(2);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
    setTransformer((r, g, b) -> new int[]{
        clamp(r + value),
        clamp(g + value),
        clamp(b + value)});
  }
}