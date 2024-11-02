package model.command;

import model.Cache;

/**
 * Adjusts the brightness of an image by adding a constant value to each color channel. Handles both
 * grayscale and color images appropriately.
 */
class Brighten extends AbstractCommand {

  private final ImageProcessor processor;
  private final int value;

  /**
   * A public constructor that input the arguments as string.
   *
   * @param rawArguments Raw Arguments string.
   */
  public Brighten(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }
    try {
      value = Integer.parseInt(getArg(0));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Expected integer argument at position 0.");
    }
    currentImage = cache.get(getArg(1));
    imageName = getArg(2);
    // set up processor to brighten image.
    processor = new ImageProcessor(currentImage, imageName, cache) {
      @Override
      public void execute() {
        processImage(createBrightenTransformer());
      }
    };
  }


  private PixelTransformer createBrightenTransformer() {
    return (r, g, b) -> {
      int[] result = new int[3];

      // Process red channel
      result[0] = r != 0 ? ImageProcessor.clamp(r + value) : 0;

      // Process other channels based on number of channels
      if (currentImage.getNoOfChannels() > 1) {
        result[1] = g != 0 ? ImageProcessor.clamp(g + value) : 0;
      }

      if (currentImage.getNoOfChannels() > 2) {
        result[2] = b != 0 ? ImageProcessor.clamp(b + value) : 0;
      }

      return result;
    };
  }

  @Override
  public void execute() {
    processor.execute();
  }
}