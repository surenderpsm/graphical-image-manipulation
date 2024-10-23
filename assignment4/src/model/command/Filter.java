package model.command;

import model.Image;

public class Filter {

  public int[][][] filterImage(Image currentImage, double[][] filter){
    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    int filterRows = filter.length;
    int filterColumns = filter[0].length;
    int[][][] imageArray = new int[height][width][3];
    int[][] redChannelData = currentImage.getRedChannelData();
    int[][] greenChannelData = currentImage.getGreenChannelData();
    int[][] blueChannelData = currentImage.getBlueChannelData();

    // padding size
    int padRowSize = filterRows /2;
    int padColSize = filterColumns/2;

    int[][] redChannelDataPadded = new int[height + 2 * padRowSize][width+ 2 * padColSize];
    int[][] greenChannelDataPadded = new int[height + 2 * padRowSize][width+ 2 * padColSize];
    int[][] blueChannelDataPadded = new int[height + 2 * padRowSize][width+ 2 * padColSize];

    // fill the padded channels with zeros and then with our image data
    for(int i=0; i< redChannelDataPadded.length; i++){
      for(int j=0; j< redChannelDataPadded[0].length; j++){
        redChannelDataPadded[i][j] = 0;
      }
    }

    for(int i=0; i< greenChannelDataPadded.length; i++){
      for(int j=0; j< greenChannelDataPadded[0].length; j++){
        greenChannelDataPadded[i][j] = 0;
      }
    }

    for(int i=0; i< blueChannelDataPadded.length; i++){
      for(int j=0; j< blueChannelDataPadded[0].length; j++){
        blueChannelDataPadded[i][j] = 0;
      }
    }

    // fill in the image data into our padded channels
    for(int i=0; i< height; i++){
      for(int j=0; j< width; j++){
        redChannelDataPadded[i+padRowSize][j+padColSize] = redChannelData[i][j];
      }
    }

    for(int i=0; i< height; i++){
      for(int j=0; j< width; j++){
        greenChannelDataPadded[i+padRowSize][j+padColSize] = greenChannelData[i][j];
      }
    }

    for(int i=0; i< height; i++){
      for(int j=0; j< width; j++){
        blueChannelDataPadded[i+padRowSize][j+padColSize] = blueChannelData[i][j];
      }
    }


    double sumRed = 0.0;
    double sumBlue = 0.0;
    double sumGreen = 0.0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        sumRed = 0;
        sumGreen = 0;
        sumBlue = 0;
        for (int a = 0; a < filterRows; a++) {
          for (int b = 0; b < filterColumns; b++) {
            sumRed += redChannelDataPadded[i + a][j + b] * filter[a][b];
            sumGreen += greenChannelDataPadded[i + a][j + b] * filter[a][b];
            sumBlue += blueChannelDataPadded[i + a][j + b] * filter[a][b];
          }
        }
        imageArray[i][j][0] = Math.min(255, Math.max(0,(int) Math.round(sumRed)));
        imageArray[i][j][1] = Math.min(255, Math.max(0,(int) Math.round(sumGreen)));
        imageArray[i][j][2] = Math.min(255, Math.max(0,(int) Math.round(sumBlue)));

      }
    }
    return imageArray;

  }

}
