package model.command;

class BlueComponent extends AbstractColorComponent {
  public BlueComponent(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    imageName = getArg(1);
  }

  public void execute(){

    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    int noOfChannels = currentImage.getNoOfChannels();

    int[][][] imageArray = new int[height][width][3];
    int[][] blueChannelData = currentImage.getBlueChannelData();

    for(int i=0; i< height; i++){
      for(int j=0;j<width;j++){
        imageArray[i][j][0] =  blueChannelData[i][j];
      }
    }

    for(int i=0; i< height; i++){
      for(int j=0;j<width;j++){
        imageArray[i][j][1] =  blueChannelData[i][j];
      }
    }

    for(int i=0; i< height; i++){
      for(int j=0;j<width;j++){
        imageArray[i][j][2] =  blueChannelData[i][j];
      }
    }

    Image blueComp = new Image(imageArray);
    Image.Cache.set(imageName, blueComp);
  }

}
