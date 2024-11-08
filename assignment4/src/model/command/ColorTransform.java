package model.command;


import model.Cache;

/**
 * Abstract base class for color transformation operations that apply a 3x3 matrix transformation to
 * RGB color values. This class provides the foundation for implementing specific color
 * transformations such as sepia, grayscale, or custom color filters.
 */

abstract class ColorTransform extends Abstract2ArgSimpleImageProcessor {

  /**
   * Constructs a new color transformation processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param matrix       A transformation matrix to be applied to RGB values
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */

  public ColorTransform(String rawArguments, double[][] matrix, Cache cache) {
    super(rawArguments, cache, (r, g, b) -> {
      int[] result = new int[3];
      for (int i = 0; i < 3; i++) {
        result[i] = clamp((int) Math.round(matrix[i][0] * r + matrix[i][1] * g + matrix[i][2] * b));
      }
      return result;
    });
  }
}