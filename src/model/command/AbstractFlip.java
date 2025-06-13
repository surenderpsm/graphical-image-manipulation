package model.command;

import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;

/**
 * an abstract class for any flip class. be it horizontal or vertical flip command classes. Abstract
 * base class for image flipping operations. Provides a framework for implementing horizontal and
 * vertical image flips while preserving all image channels.
 */

abstract class AbstractFlip extends Abstract2ArgCommand {

  /**
   * Constructs a new AbstractFlip operation.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache        The cache containing stored images
   */

  protected AbstractFlip(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Gets the transformed row index for the flip operation.
   *
   * @param currentRow The current row index
   * @return The transformed row index
   */

  protected abstract int getRowIndex(int currentRow);

  /**
   * Gets the transformed column index for the flip operation.
   *
   * @param currentCol The current column index
   * @return The transformed column index
   */

  protected abstract int getColIndex(int currentCol);

  /**
   * Executes the flip operation on all channels of the image.
   */

  @Override
  public void execute() {

    int noOfChannels = currentImage.getNoOfChannels();

    int[][][] imageArray = new int[height][width][noOfChannels];

    // Process all channels using the same flipping logic
    processChannel(imageArray, currentImage.getRedChannelData(), 0);
    processChannel(imageArray, currentImage.getGreenChannelData(), 1);
    processChannel(imageArray, currentImage.getBlueChannelData(), 2);

    if (noOfChannels == 4) {
      processChannel(imageArray, currentImage.getTransparencyData(), 3);
    }

    Image flippedImage = new Image(imageArray);
    cache.set(imageName, flippedImage);
  }

  /**
   * method to process one channel at a time.
   *
   * @param imageArray   the image as a 3d array.
   * @param channelData  channel data as a 2d array.
   * @param channelIndex channel index.
   */
  private void processChannel(int[][][] imageArray, int[][] channelData, int channelIndex) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][channelIndex] = channelData[getRowIndex(i)][getColIndex(j)];
      }
    }
  }
}
