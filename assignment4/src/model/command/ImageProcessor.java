package model.command;

import model.Cache;
import model.Image;

/**
 * Abstract base class for image processing operations. This class provides a common framework for
 * implementing various image processing commands such as color transformations, brightness
 * adjustments, and component extractions.
 *
 * <p>The class handles common image processing tasks including:
 * <ul>
 *   <li>Pixel iteration across the image
 *   <li>Channel data management
 *   <li>Result image creation and caching
 *   <li>Value bounds checking and clamping
 * </ul>
 *
 * <p>To implement a new image processing operation:
 * <ol>
 *   <li>Create a new class extending {@code ImageProcessor}
 *   <li>Implement the {@code execute()} method using {@code processImage()}
 *   <li>Provide a {@code PixelTransformer} implementation for your specific transformation
 * </ol>
 *
 * <p>Example implementation for a sepia tone effect:
 * <pre>
 * class SepiaProcessor extends ImageProcessor {
 *     public SepiaProcessor(String rawArguments) {
 *         super(rawArguments);
 *     }
 *
 *     {@literal @}Override
 *     public void execute() {
 *         processImage(new PixelTransformer() {
 *             {@literal @}Override
 *             public int[] transformPixel(int r, int g, int b) {
 *                 int newRed = clamp((int)(0.393*r + 0.769*g + 0.189*b));
 *                 int newGreen = clamp((int)(0.349*r + 0.686*g + 0.168*b));
 *                 int newBlue = clamp((int)(0.272*r + 0.534*g + 0.131*b));
 *                 return new int[]{newRed, newGreen, newBlue};
 *             }
 *         });
 *     }
 * }
 * </pre>
 *
 * @see PixelTransformer
 * @see Image
 */
abstract class ImageProcessor extends AbstractCommand {

  protected int workingWidth;
  protected int height;
  protected int width;
  protected int split = 100;

  protected int[][][] imageArray;

  /**
   * Constructs a new ImageProcessor with the specified raw arguments.
   *
   * @param rawArguments the space-separated string of command arguments
   * @throws IllegalArgumentException if the arguments are invalid or insufficient
   */
  protected ImageProcessor(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    checkForSplit();
  }

  /**
   * Constructor for internal use.
   *
   * @param image     Image
   * @param imageName String
   */
  protected ImageProcessor(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
    checkForSplit();
  }

  /**
   * Processes the current image using the specified pixel transformer. This method handles the
   * iteration over all pixels in the image, applies the transformation, and stores the result in
   * the image cache.
   *
   * <p>The method:
   * <ul>
   *   <li>Creates a new image array with the same dimensions
   *   <li>Iterates through each pixel
   *   <li>Applies the transformer to each pixel
   *   <li>Creates a new image from the transformed data
   *   <li>Stores the result in the image cache
   * </ul>
   *
   * @throws IllegalStateException if the current image is null or invalid
   */
  protected abstract void processImage();

  /**
   * Clamps a value to the valid pixel intensity range [0, 255]. This utility method ensures that
   * color values stay within the valid bounds for image data.
   *
   * @param value the value to clamp
   * @return the clamped value, guaranteed to be between 0 and 255 inclusive
   */
  protected static int clamp(int value) {
    return Math.min(255, Math.max(0, value));
  }

  /**
   * Executes the image processing operation, handling split processing if specified.
   */

  public void execute() {
    calculateWorkingWidth();
    processImage();
  }

  /**
   * method to handle if split was also provided as part of the input for the operation.
   */
  private void checkForSplit() {
    try {
      // Check if the 2nd last argument is "split"
      if (getArg(numberOfArgs() - 2).equals("split")) {
        // can the last argument be parsed?
        split = parseInt(numberOfArgs() - 1, 0, 100);
        setNumberOfArgs(numberOfArgs() - 2);
      }
    } catch (IndexOutOfBoundsException ignored) {
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Incorrect use of split command: " + e.getMessage());
    }
  }

  /**
   * method to calculate working width.
   */
  private void calculateWorkingWidth() {
    if (width == 0) {
      throw new IllegalStateException("Internal error: width not set.");
    }
    workingWidth = (int) ((double) split / 100 * width);
    imageArray = (split < 100) ? currentImage.getImageArray().clone() : new int[height][width][3];
  }

}