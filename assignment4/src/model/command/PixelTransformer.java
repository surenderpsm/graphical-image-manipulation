package model.command;

/**
 * Interface for transforming RGB pixel values in image processing operations. Implementations of
 * this interface define how individual pixels should be transformed during image processing tasks
 * such as color adjustments, filtering, or component extraction.
 *
 * <p>The interface provides a single method {@code transformPixel} that takes
 * the RGB values of a pixel and returns the transformed values. All color values are represented as
 * integers in the range [0, 255].
 *
 * <p>Example usage for increasing brightness:
 * <pre>
 * PixelTransformer brighten = new PixelTransformer() {
 *     {@literal @}Override
 *     public int[] transformPixel(int r, int g, int b) {
 *         int increment = 50;
 *         return new int[]{
 *             Math.min(255, r + increment),
 *             Math.min(255, g + increment),
 *             Math.min(255, b + increment)
 *         };
 *     }
 * };
 * </pre>
 *
 * @see AbstractImageProcessor
 */
interface PixelTransformer {

  /**
   * Transforms a single pixel's RGB values according to a specific image processing operation.
   *
   * <p>This method takes the original RGB values of a pixel and returns
   * an array containing the transformed RGB values. The implementation should ensure that the
   * returned values are within the valid range of [0, 255].
   *
   * @param r the red component value of the pixel (0-255)
   * @param g the green component value of the pixel (0-255)
   * @param b the blue component value of the pixel (0-255)
   * @return an integer array of length 3 containing the transformed [R, G, B] values, each in the
   *     range [0, 255]
   * @throws IllegalArgumentException if the input values are outside the valid range of [0, 255]
   */
  int[] transformPixel(int r, int g, int b);
}
