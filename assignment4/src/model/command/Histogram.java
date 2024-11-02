package model.command;

import model.Image;

class Histogram extends Abstract2ArgCommand{


  public Histogram(String rawArguments) {
    super(rawArguments);

  }

  @Override
  public void execute() {
    int[][] histogram = new int[3][256]; // 2D array for R, G, B channels

    // Iterate over the image array
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // For each pixel, increment the corresponding bin in each channel's histogram
        int red = currentImage.getRedChannelData()[i][j];
        int green = currentImage.getGreenChannelData()[i][j];
        int blue = currentImage.getBlueChannelData()[i][j];

        // Increment the histogram bin for each channel
        histogram[0][red]++;  // Red channel
        histogram[1][green]++; // Green channel
        histogram[2][blue]++;  // Blue channel
      }
    }
    Image.Cache.set(imageName, new Histogram(histogram));
  }
}
