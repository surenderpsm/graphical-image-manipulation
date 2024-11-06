package model.command;

import model.Cache;
import model.Image;

abstract class Filter extends AbstractImageProcessor {

  private final double[][] filter;
  private final int height;
  private final int width;
  private final int filterRows;
  private final int filterColumns;

  protected Filter(String rawArguments, double[][] filter, Cache cache) {
    super(rawArguments, cache);
    this.filter = filter;
    this.height = currentImage.getHeight();
    this.width = currentImage.getWidth();
    this.filterRows = filter.length;
    this.filterColumns = filter[0].length;
  }

  protected Filter(Image image,
                   String imageName,
                   Cache cache,
                   double[][] filter) {
    super(image, imageName, cache);
    this.filter = filter;
    this.height = currentImage.getHeight();
    this.width = currentImage.getWidth();
    this.filterRows = filter.length;
    this.filterColumns = filter[0].length;
  }

  @Override
  protected void processImage() {
    int[][][] imageArray = new int[height][width][3];

    int[][] redChannelDataPadded = createAndFillPaddedChannel(currentImage.getRedChannelData());
    int[][] greenChannelDataPadded = createAndFillPaddedChannel(currentImage.getGreenChannelData());
    int[][] blueChannelDataPadded = createAndFillPaddedChannel(currentImage.getBlueChannelData());

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
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
