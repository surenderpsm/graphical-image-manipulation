package model.command;

import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;

/**
 * A simple image processor that works with Image objects and works with the PixelTransformer
 * pattern for image processing operations. This class extends ImageProcessor to provide a
 * streamlined framework for pixel-by-pixel transformations.
 */

abstract class SimpleImageProcessor extends ImageProcessor {

  private PixelTransformer transformer;

  /**
   * Constructs a new SimpleImageProcessor with the specified arguments and transformer.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache        The cache containing stored images
   * @param transformer  The PixelTransformer to use for image processing
   */

  protected SimpleImageProcessor(ArgumentWrapper rawArguments, Cache cache, PixelTransformer transformer) {
    super(rawArguments, cache);
    this.transformer = transformer;
  }

  /**
   * Constructs a new SimpleImageProcessor with the specified arguments.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache        The cache containing stored images
   */

  protected SimpleImageProcessor(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Sets the PixelTransformer to be used for image processing.
   *
   * @param transformer The PixelTransformer to use
   */

  protected void setTransformer(PixelTransformer transformer) {
    this.transformer = transformer;
  }

  /**
   * Processes the current image using the set transformer.
   *
   * @throws IllegalStateException if height or width are not set, or if no transformer is set
   */

  @Override
  protected void processImage() {
    if (height == 0 || width == 0) {
      throw new IllegalStateException("Internal error: height or width not set.");
    }
    if (transformer == null) {
      throw new IllegalStateException("Internal error: No transformer set");
    }

    int[][] redChannel = currentImage.getRedChannelData();
    int[][] greenChannel = currentImage.getGreenChannelData();
    int[][] blueChannel = currentImage.getBlueChannelData();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < workingWidth; j++) {
        int[] transformedPixel = transformer.transformPixel(redChannel[i][j],
                                                            greenChannel[i][j],
                                                            blueChannel[i][j]);
        imageArray[i][j] = transformedPixel;
      }
    }

    Image processedImage = new Image(imageArray);
    cache.set(imageName, processedImage);
  }
}
