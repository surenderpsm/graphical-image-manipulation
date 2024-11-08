package model;

/**
 * this class represents a histogram object.
 */

public class Histogram {

  /**
   * we use a 2d array to store the histogram objects data values.
   */

  private final int[][] histogram = new int[3][256];

  /**
   * this is the constructor that takes in an image object and makes a histogram out of it and.
   * stores it in the above 2d array.
   *
   * @param image the image object for which we want the histogram.
   */
  public Histogram(Image image) {
    // Iterate over the image array
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        // For each pixel, increment the corresponding bin in each channel's histogram
        int red = image.getRedChannelData()[i][j];
        int green = image.getGreenChannelData()[i][j];
        int blue = image.getBlueChannelData()[i][j];

        // Increment the histogram bin for each channel
        histogram[0][red]++;  // Red channel
        histogram[1][green]++; // Green channel
        histogram[2][blue]++;  // Blue channel
      }
    }
  }

  /**
   * getter function that returns a 2d array with our histogram data.
   *
   * @return 2d histogram array.
   */
  public int[][] getHistogram() {
    return histogram;
  }

  /**
   * get the frequency value for a channel and a specific intensity value.
   *
   * @param channel channel 0-r , 1-g, 2-b .
   * @param value   intensity value from 0-255.
   * @return frequency value.
   */
  public int getChannelFrequencyValue(int channel, int value) {
    return histogram[channel][value];
  }
}
