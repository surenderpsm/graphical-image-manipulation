package model.command;

import model.Image;

class ValueComponent extends AbstractCommand {


  public ValueComponent(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    imageName = getArg(1);
  }

  public void execute() {
    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    int[][][] imageArray = new int[height][width][3];
    int max1 = 0;
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        max1 = Math.max(redChannelData[i][j], greenChannelData[i][j]);
        imageArray[i][j][0] = Math.max(max1, blueChannelData[i][j]);
        imageArray[i][j][1] = Math.max(max1, blueChannelData[i][j]);
        imageArray[i][j][2] = Math.max(max1, blueChannelData[i][j]);
      }
    }
    Image valueComp = new Image(imageArray);
    Image.Cache.set(imageName, valueComp);
  }

}
