package model.command;


import model.Image;

class Brighten extends AbstractCommand {

  private final int value;

  Brighten(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }

    value = Integer.parseInt(getArg(0));
    currentImage = Image.Cache.get(getArg(1));
    imageName = getArg(2);
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
        if (redChannelData[i][j] != 0){
          imageArray[i][j][0] = (redChannelData[i][j] + value);
        }
      }
    }

    if (currentImage.getNoOfChannels() > 1) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (greenChannelData[i][j] != 0){
            imageArray[i][j][1] = (greenChannelData[i][j] + value);
          }
        }
      }
    }

    if (currentImage.getNoOfChannels() > 2) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (blueChannelData[i][j] != 0){
            imageArray[i][j][2] = (blueChannelData[i][j] + value);
          }
        }
      }
    }


    Image brightImage = new Image(imageArray);
    Image.Cache.set(imageName, brightImage);

  }

}
