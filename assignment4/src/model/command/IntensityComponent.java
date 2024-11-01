package model.command;

class IntensityComponent extends ImageProcessor {

  public IntensityComponent(String rawArguments) {
    super(rawArguments);
  }

  @Override
  public void execute() {
    processImage(new PixelTransformer() {
      @Override
      public int[] transformPixel(int r, int g, int b) {
        int intensity = clamp((int) Math.round((r + g + b) / 3.0));
        return new int[]{intensity, intensity, intensity};
      }
    });
  }
}
