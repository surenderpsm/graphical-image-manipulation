package model.command;

import model.Image;

/**
 * An abstract class for getting the color component of an image.
 */
abstract class AbstractColorComponent extends Abstract2ArgCommand {

  AbstractColorComponent(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    imageName = getArg(1);
  }

  @Override
  public void execute() {
    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    //int noOfChannels = currentImage.getNoOfChannels();
    // @todo deal with transparency
    int[][][] imageArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j] = getPixel(i, j);
      }
    }

    Image.Cache.set(imageName, new Image(imageArray));
  }

  protected abstract int[] getPixel(int x, int y);

  private int addTransparency(int x, int y) {
    return currentImage.getTransparencyData()[x][y];
  }
}
