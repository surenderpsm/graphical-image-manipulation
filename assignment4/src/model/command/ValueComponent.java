package model.command;

import model.Cache;
/**
 * Creates a grayscale image based on the maximum value of RGB components for each pixel.
 * <p>
 * The value component represents the brightness of a pixel, determined by the highest
 * value among its red, green, and blue components.
 */

class ValueComponent extends Abstract2ArgSimpleImageProcessor {

  /**
   * Constructs a new ValueComponent processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */
  public ValueComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache, (r, g, b) ->
    {
      int value = Math.max(Math.max(r, g), b);
      return new int[]{
          value,
          value,
          value};
    });
  }

}
