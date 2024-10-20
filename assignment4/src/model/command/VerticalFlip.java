package model.command;

class VerticalFlip extends AbstractCommand {

  public VerticalFlip(String rawArguments) {
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

    int[][][] imageArray = new int[height][width][noOfChannels];
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();
    int[][] transparency = currentImage.getTransparencyData();

    for(int i=0, k=height-1; i< height; i++,k--){
      for(int j=0;j<width;j++){
        imageArray[i][j][0] =  redChannelData[k][j];
      }
    }

    for(int i=0, k=height-1; i< height; i++,k--){
      for(int j=0;j<width;j++){
        imageArray[i][j][1] =  greenChannelData[k][j];
      }
    }

    for(int i=0, k=height-1; i< height; i++,k--){
      for(int j=0;j<width;j++){
        imageArray[i][j][2] =  blueChannelData[k][j];
      }
    }

    if(noOfChannels == 4){
      for(int i=0, k=height-1; i< height; i++,k--){
        for(int j=0;j<width;j++){
          imageArray[i][j][3] =  transparency[k][j];
        }
      }

    }


    Image vFlipped = new Image(imageArray);
    Image.Cache.set(imageName, vFlipped);
  }


}
