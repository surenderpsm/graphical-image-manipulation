package controller.imagehandler;

import java.io.File;

/**
 * The {@code AbstractImageHandler} class provides a base implementation for image handlers,
 * encapsulating common functionality related to handling file paths and extensions.
 *
 * <p>
 * This abstract class is intended to be extended by specific image handler implementations (e.g.,
 * {@code PNGImageHandler}, {@code JPGImageHandler}) for different image formats. It provides basic
 * functionality such as storing the file path and file extension, which are essential for loading
 * and saving image files in the derived classes.
 * </p>
 *
 * <p>
 * The controller passes the file path and extension to the constructor, and the specific image
 * handler classes use this information to process the image according to its format.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 *   // A derived class like PNGImageHandler would use this base class
 *   public class PNGImageHandler extends AbstractImageHandler {
 *     public PNGImageHandler(String path) {
 *       super(path, "png");
 *     }
 *     // Implement loadImage() and saveImage() methods specific to PNG format
 *   }
 * </pre>
 */
abstract class AbstractImageHandler implements ImageHandler {

  private final File path;
  private final String extension;

  /**
   * Constructs an {@code AbstractImageHandler} with the specified file path and extension.
   *
   * <p>
   * This constructor initializes the file path and file extension. Derived classes will use this
   * base functionality to process image files based on their format.
   * </p>
   *
   * @param path      the file path of the image
   * @param extension the file extension (e.g., "png", "jpg", "ppm")
   */
  AbstractImageHandler(String path, String extension) {
    this.path = new File(path);
    this.extension = extension;
  }

  /**
   * Returns the file extension associated with the image file.
   *
   * @return the file extension (e.g., "png", "jpg", "ppm")
   */
  protected String getExtension() {
    return extension;
  }

  /**
   * Returns the {@code File} object representing the path to the image file.
   *
   * @return the {@code File} object for the image file
   */
  protected File getPath() {
    return path;
  }
}
