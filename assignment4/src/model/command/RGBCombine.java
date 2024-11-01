package model.command;


import model.Image;

class RGBCombine extends AbstractCommand {

  private final Image redImage;
  private final Image greenImage;
  private final Image blueImage;

  public RGBCombine(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 4 arguments.");
    }
    redImage = Image.Cache.get(getArg(1));
    greenImage = Image.Cache.get(getArg(2));
    blueImage = Image.Cache.get(getArg(3));
    // need to validate if all three images have same height and width.
    if (redImage.getHeight() != greenImage.getHeight()
        || redImage.getHeight() != blueImage.getHeight()
        || redImage.getWidth() != greenImage.getWidth()
        || redImage.getWidth() != blueImage.getWidth()) {
      throw new IllegalArgumentException("RGB image has incorrect dimensions.");
    }
    imageName = getArg(0);
  }

  public void execute() {

    int height = redImage.getHeight();
    int width = redImage.getWidth();

    int[][][] imageArray = new int[height][width][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        imageArray[i][j][0] = redImage.getRedChannelData()[i][j];
        imageArray[i][j][1] = greenImage.getGreenChannelData()[i][j];
        imageArray[i][j][2] = blueImage.getBlueChannelData()[i][j];
      }
    }

    Image rgbCombine = new Image(imageArray);
    Image.Cache.set(imageName, rgbCombine);
  }

}
