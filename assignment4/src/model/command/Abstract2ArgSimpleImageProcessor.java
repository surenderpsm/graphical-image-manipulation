package model.command;

import model.Cache;
import model.Image;

/**
 * An abstract base class for image processing operations that require exactly two arguments.
 * This class extends SimpleImageProcessor and handles validation and initialization of
 * two-argument image processing commands.
 *
 * <p>The class expects two arguments:
 * <ul>
 *   <li>First argument: The source image name to process
 *   <li>Second argument: The destination image name for the result
 * </ul>
 */

abstract class Abstract2ArgSimpleImageProcessor extends SimpleImageProcessor {

  /**
   * Constructs a new Abstract2ArgSimpleImageProcessor with the specified arguments.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache The cache containing stored images
   * @throws IllegalArgumentException if the number of arguments is not exactly 2
   */

  protected Abstract2ArgSimpleImageProcessor(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(getArg(0));
    imageName = getArg(1);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
  }

  /**
   * Constructs a new Abstract2ArgSimpleImageProcessor with the specified arguments and transformer.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache The cache containing stored images
   * @param transformer The PixelTransformer to use for image processing
   * @throws IllegalArgumentException if the number of arguments is not exactly 2
   */

  protected Abstract2ArgSimpleImageProcessor(String rawArguments,
                                             Cache cache,
                                             PixelTransformer transformer) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(getArg(0));
    imageName = getArg(1);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
    setTransformer(transformer);
  }
}
