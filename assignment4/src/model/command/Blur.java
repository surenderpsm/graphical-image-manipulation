package model.command;


import model.Image;

class Blur extends AbstractCommand {


  public Blur(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    ImageName = getArg(1);
  }

  public void execute() {

    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    int[][][] imageArray = new int[height][width][3];
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();
    double[][] filter = {
            {1 / 16.0, 2 / 16.0, 1 / 16.0},
            {2 / 16.0, 4 / 16.0, 2 / 16.0},
            {1 / 16.0, 2 / 16.0, 1 / 16.0}
    };
    double sumRed = 0;
    double sumBlue = 0;
    double sumGreen = 0;

    for (int i = 1; i < height - 1; i++) {
      for (int j = 1; j < width - 1; j++) {
        sumRed = 0;
        sumGreen = 0;
        sumBlue = 0;
        for (int a = 0; a < 3; a++) {
          for (int b = 0; b < 3; b++) {
            sumRed += redChannelData[i + a - 1][j + b - 1] * filter[a][b];
            sumGreen += greenChannelData[i + a - 1][j + b - 1] * filter[a][b];
            sumBlue += blueChannelData[i + a - 1][j + b - 1] * filter[a][b];
          }
        }
        imageArray[i][j][0] = (int) sumRed;
        imageArray[i][j][1] = (int) sumGreen;
        imageArray[i][j][2] = (int) sumBlue;

      }
    }

    Image blurredImage = new Image(imageArray);
    Image.Cache.set(imageName, blurredImage);

  }

  @Override
  protected boolean run() {
    return false;
  }
}
