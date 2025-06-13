package model.command;


import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;

/**
 * Combines separate red, green, and blue channel images into a single RGB image. This class takes
 * three images representing the individual color channels and combines them into a full-color RGB
 * image.
 */

class RGBCombine extends AbstractCommand {

  private final Image redImage;
  private final Image greenImage;
  private final Image blueImage;

  /**
   * Constructs a new RGBCombine processor.
   *
   * @param rawArguments The command arguments in format: "destImage redImage greenImage blueImage"
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if image dimensions don't match or arguments are invalid
   */

  public RGBCombine(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 4 arguments.");
    }
    redImage = cache.get(parseString(1));
    greenImage = cache.get(parseString(2));
    blueImage = cache.get(parseString(3));
    // need to validate if all three images have same height and width.
    if (redImage.getHeight() != greenImage.getHeight()
        || redImage.getHeight() != blueImage.getHeight()
        || redImage.getWidth() != greenImage.getWidth()
        || redImage.getWidth() != blueImage.getWidth()) {
      throw new IllegalArgumentException("RGB image has incorrect dimensions.");
    }
    imageName = parseString(0);
  }

  /**
   * Executes the RGB combination operation by combining the individual channel images into a single
   * RGB image.
   */

  public void execute() {

    int height = redImage.getHeight();
    int width = redImage.getWidth();

    int[][][] imageArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][0] = redImage.getRedChannelData()[i][j];
        imageArray[i][j][1] = greenImage.getGreenChannelData()[i][j];
        imageArray[i][j][2] = blueImage.getBlueChannelData()[i][j];
      }
    }

    Image rgbCombine = new Image(imageArray);
    cache.set(imageName, rgbCombine);
  }

}
