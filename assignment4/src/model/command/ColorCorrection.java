package model.command;

import model.Cache;
import model.Histogram;

/**
 * Performs color correction on an image by analyzing and adjusting the color channels based on
 * their peak frequencies and average values. This processor identifies the peak frequency position
 * for each color channel (R,G,B) within the range of 10-245 and adjusts the channels to align with
 * their average peak position.
 */

class ColorCorrection extends Abstract2ArgSimpleImageProcessor {

  Histogram histogram;
  int average;
  // to find peakPositions frequencies of each channel
  int[] peakPositions = new int[3];


  /**
   * Constructs a new ColorCorrection processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */

  public ColorCorrection(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    histogram = new Histogram(currentImage);
    setChannelMax();
    setAverage();
    setTransformer((r, g, b) -> new int[]{
        clamp(r + getChannelOffset(0)),
        clamp(g + getChannelOffset(1)),
        clamp(b + getChannelOffset(2))});
  }

  /**
   * Identifies the peak frequency position for each color channel. Analyzes values between 10 and
   * 245 to avoid extreme outliers.
   */

  private void setChannelMax() {
    // get peak for each channel.
    for (int channel = 0; channel < 3; channel++) {
      int peakFrequency = 0;
      int maxValue = 0;
      for (int i = 10; i < 245; i++) {
        if (peakFrequency < histogram.getChannelFrequencyValue(channel, i)) {
          peakFrequency = histogram.getChannelFrequencyValue(channel, i);
          maxValue = i;
        }
      }
      this.peakPositions[channel] = maxValue;
    }
  }

  /**
   * Calculates the average peak position across all color channels.
   */

  private void setAverage() {
    int average = 0;
    for (int i : peakPositions) {
      average += i;
    }
    this.average = (int) ((double) average / peakPositions.length);
  }

  /**
   * Calculates the offset needed to align a channel with the average peak position.
   *
   * @param channel The index of the color channel (0=R, 1=G, 2=B)
   * @return The offset value to apply to the channel
   */

  private int getChannelOffset(int channel) {
    return average - peakPositions[channel];
  }

}