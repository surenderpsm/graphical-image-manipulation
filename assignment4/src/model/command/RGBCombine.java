package model.command;


import model.Image;

class RGBCombine extends AbstractCommand {

  private final Image currentImage2;
  private final Image currentImage3;

  public RGBCombine(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 4 arguments.");
    }
    currentImage = Image.Cache.get(getArg(1));
    currentImage2 = Image.Cache.get(getArg(2));
    currentImage3 = Image.Cache.get(getArg(3));
    imageName = getArg(0);
  }

  public void execute() {

    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    //int noOfChannels = currentImage.getNoOfChannels();

    int[][][] imageArray = new int[height][width][3];
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage2.getGreenChannelData();
    int[][] blueChannelData = currentImage3.getBlueChannelData();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][0] = redChannelData[i][j];
        imageArray[i][j][1] = greenChannelData[i][j];
        imageArray[i][j][2] = blueChannelData[i][j];
      }
    }

    Image rgbCombine = new Image(imageArray);
    Image.Cache.set(imageName, rgbCombine);

  }

}
