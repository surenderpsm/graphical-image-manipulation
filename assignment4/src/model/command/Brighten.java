package model.command;

import model.Cache;

/**
 * Adjusts the brightness of an image by adding a constant value to each color channel. Handles both
 * grayscale and color images appropriately.
 */
class Brighten extends AbstractCommand {

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
  }

  @Override
  public void execute() {
    new ImageProcessor(currentImage, imageName, cache) {
      @Override
      public void execute() {
        processImage((r, g, b) -> {
          return new int[]{clamp(r + value), clamp(g + value), clamp(b + value)};
        });
      }
    }.execute();
  }
}