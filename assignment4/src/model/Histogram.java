package model;

public class Histogram {

  private final int[][] histogram = new int[3][256];

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

  public int[][] getHistogram() {
    return histogram;
  }
}
