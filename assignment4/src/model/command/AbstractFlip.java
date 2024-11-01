package model.command;

import model.Image;

abstract class AbstractFlip extends Abstract2ArgCommand {

  protected int height;
  protected int width;

  public AbstractFlip(String rawArguments) {
    super(rawArguments);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
  }

  protected abstract int getRowIndex(int currentRow);

  protected abstract int getColIndex(int currentCol);

  @Override
  public void execute() {

    int noOfChannels = currentImage.getNoOfChannels();

    int[][][] imageArray = new int[height][width][noOfChannels];
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();
    int[][] transparency = currentImage.getTransparencyData();

    // Process all channels using the same flipping logic
    processChannel(imageArray, redChannelData, 0);
    processChannel(imageArray, greenChannelData, 1);
    processChannel(imageArray, blueChannelData, 2);

    if (noOfChannels == 4) {
      processChannel(imageArray, transparency, 3);
    }

    Image flippedImage = new Image(imageArray);
    Image.Cache.set(imageName, flippedImage);
  }

  private void processChannel(int[][][] imageArray, int[][] channelData, int channelIndex) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][channelIndex] = channelData[getRowIndex(i)][getColIndex(j)];
      }
    }
  }
}
