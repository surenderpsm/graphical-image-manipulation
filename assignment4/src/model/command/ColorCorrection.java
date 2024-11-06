package model.command;

import model.Cache;
import model.Histogram;

class ColorCorrection extends SimpleImageProcessor {

  Histogram histogram;
  int average;
  // to find max frequencies of each channel
  int[] max = new int[3];


  public ColorCorrection(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    histogram = new Histogram(currentImage);
    setChannelMax();
    setAverage();
    setTransformer((r,g,b)->{
      return new int[]{
          r + getChannelOffset(0),
          g + getChannelOffset(1),
          b + getChannelOffset(2)
      };
    });
  }

  private void setChannelMax() {
    // get peak for each channel.
    for(int channel = 0; channel < 3; channel++) {
      int max = 0;
      int maxi = 0;
      for (int i = 10; i < 245; i++) {
        max = Math.max(max, histogram.getChannelValue(channel, i));
        maxi = i;
      }
      this.max[channel] = maxi;
    }
  }

  private void setAverage() {
    int average = 0;
    for (int i : max) {
      average += i;
    }
    this.average = average/max.length;
  }

  private int getChannelOffset(int channel) {
    return max[channel] - average;
  }

}