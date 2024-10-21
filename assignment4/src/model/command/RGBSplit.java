package model.command;


import model.Image;

class RGBSplit extends AbstractCommand {
  private final String imageName2;
  private final String imageName3;

  public RGBSplit(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    imageName = getArg(1);
    imageName2 = getArg(2);
    imageName3 = getArg(3);
  }

  public void execute() {

    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    //int noOfChannels = currentImage.getNoOfChannels();

    int[][][] imageArray = new int[height][width][3];
    int[][][] imageArray2 = new int[height][width][3];
    int[][][] imageArray3 = new int[height][width][3];
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][0] = redChannelData[i][j];
        imageArray[i][j][1] = 0;
        imageArray[i][j][2] = 0;
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray2[i][j][0] = 0;
        imageArray2[i][j][1] = greenChannelData[i][j];
        imageArray2[i][j][2] = 0;
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray3[i][j][0] = 0;
        imageArray3[i][j][1] = 0;
        imageArray3[i][j][2] = blueChannelData[i][j];
      }
    }


    Image redComp = new Image(imageArray);
    Image greenComp = new Image(imageArray2);
    Image blueComp = new Image(imageArray3);
    Image.Cache.set(imageName, redComp);
    Image.Cache.set(imageName2, greenComp);
    Image.Cache.set(imageName3, blueComp);
  }


}
