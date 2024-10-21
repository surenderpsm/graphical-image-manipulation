package model.command;


import model.Image;

class Sharpen extends AbstractCommand {

  public Sharpen(String rawArguments) {
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
    double[][] filter = {
            {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, 1 / 4.0, 1 / 4.0, 1 / 4.0, -1 / 8.0},
            {-1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0, -1 / 8.0}
    };
    double sumRed = 0;
    double sumBlue = 0;
    double sumGreen = 0;

    for (int i = 1; i < height - 1; i++) {
      for (int j = 1; j < width - 1; j++) {
        sumRed = 0;
        sumGreen = 0;
        sumBlue = 0;
        for (int a = 0; a < 5; a++) {
          for (int b = 0; b < 5; b++) {
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

    Image sharpenedImage = new Image(imageArray);
    Image.Cache.set(imageName, sharpenedImage);

  }

}
