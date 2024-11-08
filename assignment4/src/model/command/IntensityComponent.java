package model.command;

import model.Cache;
/**
 * Processes an image to create an intensity component where each pixel's RGB values
 * are set to the average of its original RGB values.
 * <p>
 * The resulting image appears as a grayscale representation based on pixel intensity.
 */

class IntensityComponent extends Abstract2ArgSimpleImageProcessor {
  /**
   * Constructs a new IntensityComponent processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */

  public IntensityComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache, (r, g, b) ->
    {
      int intensity = clamp((int) Math.round((r + g + b) / 3.0));
      return new int[]{
          intensity,
          intensity,
          intensity};
    });
  }
}
