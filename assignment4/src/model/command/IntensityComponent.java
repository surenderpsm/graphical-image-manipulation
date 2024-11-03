package model.command;

import model.Cache;

class IntensityComponent extends ImageProcessor {

  public IntensityComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  @Override
  public void execute() {
    processImage((r, g, b) -> {
      int intensity = clamp((int) Math.round((r + g + b) / 3.0));
      return new int[]{intensity, intensity, intensity};
    });
  }
}
