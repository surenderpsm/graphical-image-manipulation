package model.command;

import model.Cache;

class IntensityComponent extends Abstract2ArgSimpleImageProcessor {

  public IntensityComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache, (r, g, b) ->
    {
      int intensity = clamp((int) Math.round((r + g + b) / 3.0));
      return new int[]{
          intensity,
          intensity,
          intensity};
    });
  }
}
