package model.command;


import model.Cache;
import utils.arguments.ArgumentWrapper;

/**
 * Command implementation for splitting an RGB image into its individual color channels. Creates
 * three separate images containing the red, green, and blue channels respectively.
 */

class RGBSplit extends AbstractCommand {

  private final String redImageName;
  private final String greenImageName;
  private final String blueImageName;

  /**
   * Constructs a new RGBSplit command.
   *
   * @param rawArguments Space-separated string containing: source image name, red image name, green
   *                     image name, blue image name
   * @param cache        The cache containing stored images
   * @throws IllegalArgumentException if the number of arguments is not exactly 4
   */

  public RGBSplit(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 4 arguments.");
    }
    currentImage = cache.get(parseString(0));
    redImageName = parseString(1);
    greenImageName = parseString(2);
    blueImageName = parseString(3);
  }

  /**
   * Executes the RGB split operation, creating three new images containing the individual color
   * channels.
   */

  public void execute() {
    new RedComponent(currentImage, redImageName, cache).execute();
    new BlueComponent(currentImage, blueImageName, cache).execute();
    new GreenComponent(currentImage, greenImageName, cache).execute();
  }

}
