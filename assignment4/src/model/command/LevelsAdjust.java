package model.command;

import static model.command.AbstractImageProcessor.clamp;

import model.Cache;

class LevelsAdjust extends AbstractCommand {

  final private int b;
  final private int m;
  final private int w;

  public LevelsAdjust(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 5) {
      throw new IllegalArgumentException("Expected 5 arguments.");
    }
    b = parseInt(0, 0, 255);
    m = parseInt(1, b, 255);
    w = parseInt(2, m, 255);
    currentImage = cache.get(getArg(3));
    imageName = getArg(4);
  }


  // levels adjust function
  public int levelsAdjust(int pValue) {
    double A = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    double Ap = -b * (128 - 255) + 128 * w - 255 * m;
    double Aq = b * b * (128 - 255) + 255 * m * m - 128 * w * w;
    double Ar = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);
    double p = Ap / A;
    double q = Aq / A;
    double r = Ar / A;
    return (int) ( p * pValue * pValue + q * pValue + r);
  }

  @Override
  public void execute() {
    new SimpleImageProcessor(currentImage, imageName, cache, (r, g, b) -> new int[]{
        clamp(levelsAdjust(r)),
        clamp(levelsAdjust(g)),
        clamp(levelsAdjust(b)),}) {
    }.execute();
  }

}