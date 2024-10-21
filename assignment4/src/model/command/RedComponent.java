package model.command;

class RedComponent extends AbstractColorComponent {
  public RedComponent(String rawArguments) {
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

    int[][][] imageArray = new int[height][width][3];
    int[][] redChannelData = currentImage.getRedChannelData();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][0] = redChannelData[i][j];
        imageArray[i][j][1] = redChannelData[i][j];
        imageArray[i][j][2] = redChannelData[i][j];

      }
    }


    Image redComp = new Image(imageArray);
    Image.Cache.set(imageName, redComp);
  }

}
