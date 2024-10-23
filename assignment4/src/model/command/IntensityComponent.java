package model.command;

import model.Image;

class IntensityComponent extends AbstractCommand {

  public IntensityComponent(String rawArguments) {
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
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][0] = Math.min(255, Math.max(0,(int) Math.round((redChannelData[i][j]
                + greenChannelData[i][j] + blueChannelData[i][j]) / 3.0)));
        imageArray[i][j][1] = Math.min(255, Math.max(0,(int) Math.round((redChannelData[i][j]
                + greenChannelData[i][j] + blueChannelData[i][j]) / 3.0)));
        imageArray[i][j][2] = Math.min(255, Math.max(0,(int) Math.round((redChannelData[i][j]
                + greenChannelData[i][j] + blueChannelData[i][j]) / 3.0)));
      }
    }

    Image intensityComp = new Image(imageArray);
    Image.Cache.set(imageName, intensityComp);

  }
}
