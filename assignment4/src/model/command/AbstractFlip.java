package model.command;

import model.Image;

abstract class AbstractFlip extends Abstract2ArgCommand {

  protected AbstractFlip(String rawArguments) {
    super(rawArguments);
  }

  protected abstract int getRowIndex(int currentRow);

  protected abstract int getColIndex(int currentCol);

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
