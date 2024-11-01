package model.command;

class LumaComponent extends ImageProcessor {

  private static final double RED_WEIGHT = 0.2126;
  private static final double GREEN_WEIGHT = 0.7152;
  private static final double BLUE_WEIGHT = 0.0722;

  public LumaComponent(String rawArguments) {
    super(rawArguments);
  }

  @Override
  public void execute() {
    processImage(new PixelTransformer() {
      @Override
      public int[] transformPixel(int r, int g, int b) {
        int luma = clamp((int) Math.round(RED_WEIGHT * r + GREEN_WEIGHT * g + BLUE_WEIGHT * b));
        return new int[]{luma, luma, luma};
      }
    });
  }

}
