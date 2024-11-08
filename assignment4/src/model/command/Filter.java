package model.command;

import model.Cache;
import model.Image;

/**
 * Abstract base class for implementing image filters using convolution matrices.
 * This class provides the framework for applying various types of filters (blur, sharpen, etc.)
 * using a convolution kernel.
 */
abstract class Filter extends ImageProcessor {

  private final double[][] filter;
  private final int filterRows;
  private final int filterColumns;

  /**
   * Constructs a new Filter with the specified filter kernel.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param filter The convolution kernel to apply
   * @param cache The cache containing stored images
   * @throws IllegalArgumentException if the number of arguments is not exactly 2
   */

  protected Filter(String rawArguments, double[][] filter, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(getArg(0));
    imageName = getArg(1);
    this.filter = filter;
    this.height = currentImage.getHeight();
    this.width = currentImage.getWidth();
    this.filterRows = filter.length;
    this.filterColumns = filter[0].length;
  }

  /**
   * Processes the image by applying the convolution filter.
   * Handles padding and maintains image boundaries.
   */
  @Override
  protected void processImage() {

    int[][] redChannelDataPadded = createAndFillPaddedChannel(currentImage.getRedChannelData());
    int[][] greenChannelDataPadded = createAndFillPaddedChannel(currentImage.getGreenChannelData());
    int[][] blueChannelDataPadded = createAndFillPaddedChannel(currentImage.getBlueChannelData());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < workingWidth; j++) {
        double sumRed = 0;
        double sumGreen = 0;
        double sumBlue = 0;
        for (int a = 0; a < filterRows; a++) {
          for (int b = 0; b < filterColumns; b++) {
            sumRed += redChannelDataPadded[i + a][j + b] * filter[a][b];
            sumGreen += greenChannelDataPadded[i + a][j + b] * filter[a][b];
            sumBlue += blueChannelDataPadded[i + a][j + b] * filter[a][b];
          }
        }
        imageArray[i][j][0] = Math.min(255, Math.max(0, (int) Math.round(sumRed)));
        imageArray[i][j][1] = Math.min(255, Math.max(0, (int) Math.round(sumGreen)));
        imageArray[i][j][2] = Math.min(255, Math.max(0, (int) Math.round(sumBlue)));
      }
    }

    Image processedImage = new Image(imageArray);
    cache.set(imageName, processedImage);
  }

  /**
   * method to pad channel if required.
   * @param sourceChannel original 2d array of the channel.
   * @return new padded channel.
   */
  private int[][] createAndFillPaddedChannel(int[][] sourceChannel) {
    // padding size
    int padRowSize = filterRows / 2;
    int padColSize = filterColumns / 2;

    int[][] paddedChannel = new int[height + 2 * padRowSize][width + 2 * padColSize];
    for (int i = 0; i < sourceChannel.length; i++) {
      System.arraycopy(sourceChannel[i], 0, paddedChannel[i + padRowSize], padColSize, width);
    }
    return paddedChannel;
  }

}
