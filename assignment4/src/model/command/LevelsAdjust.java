package model.command;

import model.Cache;
/**
 * Adjusts the levels of an image using a quadratic function defined by black, mid, and white points.
 * <p>
 * This processor implements a levels adjustment similar to image editing software, allowing for
 * fine control over the image's tonal range and contrast.
 */

class LevelsAdjust extends SimpleImageProcessor {

  final private int b;
  final private int m;
  final private int w;

  /**
   * Constructs a new LevelsAdjust processor.
   *
   * @param rawArguments The command arguments in format: "b m w sourceImage destinationImage"
   *                     where b (black point), m (mid point), and w (white point) are values between 0-255
   * @param cache The cache storing the images
   * @throws IllegalArgumentException if points are invalid or not in ascending order (b < m < w)
   */

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
    height = currentImage.getHeight();
    width = currentImage.getWidth();
    setTransformer((r, g, b) -> new int[]{
        clamp(levelsAdjust(r)),
        clamp(levelsAdjust(g)),
        clamp(levelsAdjust(b)),});
  }


  /**
   * Applies the levels adjustment function to a single pixel value.
   * Uses a quadratic function to map input values to adjusted output values.
   *
   * @param pValue The input pixel value
   * @return The adjusted pixel value
   */

  // levels adjust function
  private int levelsAdjust(int pValue) {
    double A = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    double Ap = -b * (128 - 255) + 128 * w - 255 * m;
    double Aq = b * b * (128 - 255) + 255 * m * m - 128 * w * w;
    double Ar = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);
    double p = Ap / A;
    double q = Aq / A;
    double r = Ar / A;
    return (int) ( p * pValue * pValue + q * pValue + r);
  }
}