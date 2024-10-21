package model.command;

// horizontal flip deals with columns
class HorizontalFlip extends AbstractCommand {

  public HorizontalFlip(String rawArguments) {
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
    int noOfChannels = currentImage.getNoOfChannels();

    int[][][] imageArray = new int[height][width][noOfChannels];
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();
    int[][] transparency = currentImage.getTransparencyData();

    for (int i = 0; i < height; i++) {
      for (int j = 0, k = width - 1; j < width; j++, k--) {
        imageArray[i][j][0] = redChannelData[i][k];
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0, k = width - 1; j < width; j++, k--) {
        imageArray[i][j][1] = greenChannelData[i][k];
      }
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0, k = width - 1; j < width; j++, k--) {
        imageArray[i][j][2] = blueChannelData[i][k];
      }
    }

    if (noOfChannels == 4) {
      for (int i = 0; i < height; i++) {
        for (int j = 0, k = width - 1; j < width; j++, k--) {
          imageArray[i][j][3] = transparency[i][k];
        }
      }

    }


    Image hFlipped = new Image(imageArray);
    Image.Cache.set(imageName, hFlipped);
  }

}
