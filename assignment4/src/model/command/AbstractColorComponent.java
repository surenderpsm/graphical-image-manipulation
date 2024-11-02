package model.command;

import model.Cache;
import model.Image;

/**
 * An abstract class for getting the color component of an image.
 */
abstract class AbstractColorComponent extends Abstract2ArgCommand {

  protected AbstractColorComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(getArg(0));
    imageName = getArg(1);
  }

  protected AbstractColorComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  @Override
  public void execute() {
    // @todo deal with transparency
    int[][][] imageArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j] = getPixel(i, j);
      }
    }

    cache.set(imageName, new Image(imageArray));
  }

  protected abstract int[] getPixel(int x, int y);

  private int addTransparency(int x, int y) {
    return currentImage.getTransparencyData()[x][y];
  }
}
