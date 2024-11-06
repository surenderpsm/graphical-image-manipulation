package model.command;

import model.Cache;
import model.Histogram;

class ColorCorrection extends SimpleImageProcessor {

  Histogram histogram;
  int average;
  // to find peakPositions frequencies of each channel
  int[] peakPositions = new int[3];


  public ColorCorrection(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    histogram = new Histogram(currentImage);
    setChannelMax();
    setAverage();
    setTransformer((r,g,b)-> new int[]{
        clamp(r + getChannelOffset(0)),
        clamp(g + getChannelOffset(1)),
        clamp(b + getChannelOffset(2))
    });
  }

  private void setChannelMax() {
    // get peak for each channel.
    for(int channel = 0; channel < 3; channel++) {
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

  private void setAverage() {
    int average = 0;
    for (int i : peakPositions) {
      average += i;
    }
    this.average = (int) ((double) average/ peakPositions.length);
  }

  private int getChannelOffset(int channel) {
    return average - peakPositions[channel];
  }

}