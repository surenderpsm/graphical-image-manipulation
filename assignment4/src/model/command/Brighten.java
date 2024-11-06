package model.command;

import static model.command.ImageProcessor.clamp;

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
    value = parseInt(0);
    currentImage = cache.get(getArg(1));
    imageName = getArg(2);
  }

  @Override
  public void execute() {
    new SimpleImageProcessor(currentImage, imageName, cache, (r, g, b) -> new int[]{
        clamp(r + value),
        clamp(g + value),
        clamp(b + value)}) {
    }.execute();
  }
}