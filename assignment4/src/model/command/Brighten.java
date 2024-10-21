package model.command;


class Brighten extends AbstractCommand {

  private final String brightenImageName;

  Brighten(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }

    int value = Integer.parseInt(getArg(0));
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
        if (redChannelData[i][j] ! = 0 && redChannelData != null){
          imageArray[i][j][0] = (redChannelData[i][j] + value);
        }
      }
    }

    if (currentImage.getNoOfChannels() > 1) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (greenChannelData[i][j] ! = 0 && greenChannelData != null){
            imageArray[i][j][1] = (greenChannelData[i][j] + value);
          }
        }
      }
    }

    if (currentImage.getNoOfChannels() > 2) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (blueChannelData[i][j] ! = 0 && blueChannelData != null){
            imageArray[i][j][2] = (blueChannelData[i][j] + value);
          }
        }
      }
    }


    Image brightImage = new Image(imageArray);
    Image.Cache.set(imageName, brightImage);

  }

  @Override
  protected boolean run() {
    return false;
  }

}
