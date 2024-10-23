package model.command;


import model.Image;

class Brighten extends AbstractCommand {

  private final int value;

  Brighten(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }
    try {
      value = Integer.parseInt(getArg(0));
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Expected integer argument at position 0.");
    }
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
    int temp;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (redChannelData[i][j] != 0){
          temp = (redChannelData[i][j] + value);
          if(temp>255){
            temp = 255;
          }
          else if(temp<0){
            temp = 0;
          }
          imageArray[i][j][0] = temp;
        }
      }
    }

    if (currentImage.getNoOfChannels() > 1) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (greenChannelData[i][j] != 0){
            temp = (greenChannelData[i][j] + value);
            if(temp>255){
              temp = 255;
            }
            else if(temp<0){
              temp = 0;
            }
            imageArray[i][j][1] = temp;
          }
        }
      }
    }

    if (currentImage.getNoOfChannels() > 2) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (blueChannelData[i][j] != 0){
            temp = (blueChannelData[i][j] + value);
            if(temp>255){
              temp = 255;
            }
            else if(temp<0){
              temp = 0;
            }
            imageArray[i][j][2] = temp;
          }
        }
      }
    }


    Image brightImage = new Image(imageArray);
    Image.Cache.set(imageName, brightImage);

  }

}
