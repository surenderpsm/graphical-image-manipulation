package model.command;


import model.Image;

class ColorTransform extends AbstractCommand {
  protected double a11;
  protected double a12;
  protected double a13;
  protected double a21;
  protected double a22;
  protected double a23;
  protected double a31;
  protected double a32;
  protected double a33;

  public ColorTransform(String rawArguments) {
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

    for (int i = 1; i < height - 1; i++) {
      for (int j = 1; j < width - 1; j++) {
        imageArray[i][j][0] = (int) (a11 * redChannelData[i][j] + a12 * greenChannelData[i][j]
                + a13 * blueChannelData[i][j]);
        imageArray[i][j][1] = (int) (a21 * redChannelData[i][j] + a22 * greenChannelData[i][j]
                + a23 * blueChannelData[i][j]);
        imageArray[i][j][2] = (int) (a31 * redChannelData[i][j] + a32 * greenChannelData[i][j]
                + a33 * blueChannelData[i][j]);
      }
    }

    Image transformedImage = new Image(imageArray);
    Image.Cache.set(imageName, transformedImage);

  }

}