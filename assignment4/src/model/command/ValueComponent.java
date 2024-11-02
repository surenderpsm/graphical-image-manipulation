package model.command;

import model.Cache;

class ValueComponent extends ImageProcessor {


  public ValueComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  @Override
  public void execute() {
    processImage(new PixelTransformer() {
      @Override
      public int[] transformPixel(int r, int g, int b) {
        int value = Math.max(Math.max(r, g), b);
        return new int[]{value, value, value};
      }
    });
  }

}
