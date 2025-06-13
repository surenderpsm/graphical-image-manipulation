package model.command;

import model.Cache;
import utils.arguments.ArgumentWrapper;

/**
 * Creates a grayscale image based on the luma (perceived brightness) of each pixel. Uses
 * standardized weights for RGB channels to calculate perceived brightness: Red (0.2126), Green
 * (0.7152), and Blue (0.0722).
 */

class LumaComponent extends Abstract2ArgSimpleImageProcessor {

  private static final double RED_WEIGHT = 0.2126;
  private static final double GREEN_WEIGHT = 0.7152;
  private static final double BLUE_WEIGHT = 0.0722;

  /**
   * Constructs a new LumaComponent processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */

  public LumaComponent(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache, (r, g, b) -> {
      int luma = clamp((int) Math.round(RED_WEIGHT * r + GREEN_WEIGHT * g + BLUE_WEIGHT * b));
      return new int[]{
          luma,
          luma,
          luma};
    });
  }
}
