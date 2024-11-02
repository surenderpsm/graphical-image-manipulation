package model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class represents an image.
 *
 * @see Image.Cache
 */
public class Image {

  // isEmpty tracks whether an Image is instantiated as an empty object or with parameters.
  private boolean isEmpty = false;



  private int height;
  private int width;
  private int noOfChannels;
  private int[][] redChannelData;
  private int[][] greenChannelData;
  private int[][] blueChannelData;
  private int[][] transparency;

  /**
   * This constructor is used to instantiate an "empty" Image object.
   */
  public Image() {
    isEmpty = true;
  }

  /**
   * method to construct the image object.
   */

  public Image(int[][][] arr) {
    height = arr.length;
    width = arr[0].length;
    noOfChannels = arr[0][0].length;

    redChannelData = new int[height][width];
    greenChannelData = new int[height][width];
    blueChannelData = new int[height][width];

    if (noOfChannels == 4) {
      transparency = new int[height][height];
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        redChannelData[i][j] = arr[i][j][0];
        if (noOfChannels > 1) {
          greenChannelData[i][j] = arr[i][j][1];
        }

        if (noOfChannels > 2) {
          blueChannelData[i][j] = arr[i][j][2];
        }

        if (noOfChannels == 4) {
          transparency[i][j] = arr[i][j][3];

        }
      }
    }
  }

  /**
   * method to get height of image data.
   * @return  int array.
   */

  public int getHeight() {
    return height;
  }

  /**
   * method to get width of image data.
   * @return  int array.
   */

  public int getWidth() {
    return width;
  }

  /**
   * method to get no of channels of image data.
   * @return  int array.
   */

  public int getNoOfChannels() {
    return noOfChannels;
  }

  /**
   * Checks if the Image is empty.
   *
   * @return is empty or not
   */
  public boolean isEmpty() {
    return isEmpty;
  }

  /**
   * method to get red channel data.
   * @return  int array.
   */

  public int[][] getRedChannelData() {
    return redChannelData;
  }

  /**
   * method to get green channel data.
   * @return  int array.
   */

  public int[][] getGreenChannelData() {
    if (noOfChannels < 2) {
      return null;
    }
    return greenChannelData;
  }

  /**
   * method to get blue channel data.
   * @return  int array.
   */

  public int[][] getBlueChannelData() {
    if (noOfChannels < 3) {
      return null;
    }
    return blueChannelData;
  }

  /**
   * method to get transparency.
   * @return  int array.
   */

  public int[][] getTransparencyData() {
    if (noOfChannels < 4) {
      return null;
    }
    return transparency;
  }

  /**
   * method to get image pixel data in array format(r,g,b).
   * @return  int array.
   */

  public int[] getPixelData(int i, int j) {
    int[] arr = new int[noOfChannels];
    if (noOfChannels == 3) {
      arr[0] = redChannelData[i][j];
      arr[1] = greenChannelData[i][j];
      arr[2] = blueChannelData[i][j];
    }
    else if (noOfChannels == 4) {

      arr[0] = redChannelData[i][j];
      arr[1] = greenChannelData[i][j];
      arr[2] = blueChannelData[i][j];
      arr[3] = transparency[i][j];
    }
    return arr;
  }

  /**
   * method to get image in array format.
   * @return 3d int array.
   */

  public int[][][] getImageArray() {
    int[][][] imageArr = new int[this.height][this.width][this.noOfChannels];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        imageArr[i][j][0] = redChannelData[i][j];
      }
    }

    if (noOfChannels > 1) {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          imageArr[i][j][1] = greenChannelData[i][j];
        }
      }
    }

    if (noOfChannels > 2) {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          imageArr[i][j][2] = blueChannelData[i][j];
        }
      }
    }
    if (noOfChannels == 4) {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          imageArr[i][j][3] = transparency[i][j];
        }
      }
    }

    return imageArr;
  }
}
