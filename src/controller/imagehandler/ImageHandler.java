package controller.imagehandler;

import java.io.IOException;
import model.Image;

/**
 * The {@code ImageHandler} interface provides an abstraction for loading and saving images. It
 * defines the basic operations required for handling various image file formats, and specific
 * implementations of this interface handle different formats (e.g., PNG, JPEG, PPM, etc.).
 *
 * <p>
 * This interface is primarily used in the controller to delegate the tasks of loading and saving
 * images. The controller is responsible for determining which implementation of
 * {@code ImageHandler} to use based on the file path or extension.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 *   // In the controller, handling file paths based on extension:
 *   ImageHandler pngHandler = getMatchingImageHandler("../koala.png");
 *   ImageHandler ppmHandler = getMatchingImageHandler("../koala.ppm");
 *
 *   // In the model:
 *   Image image = pngHandler.loadImage();   // Load PNG image
 *   ppmHandler.saveImage(imageData);       // Save to PPM format
 * </pre>
 * <p>
 * The model interacts with the {@code ImageHandler} interface to load or save images, but it does
 * not need to worry about the specifics of the file format. The controller provides the appropriate
 * {@code ImageHandler} instance based on the image file format.
 * </p>
 */
public interface ImageHandler {

  /**
   * Loads an image from a specified file path.
   *
   * <p>
   * This method is called by the model using the file path provided by the controller. The image
   * data is returned in an {@code Image} object, which contains the pixel data (e.g., red, green,
   * blue, and alpha channels) along with the image's dimensions (width and height).
   * </p>
   *
   * @return an {@code Image} object containing the pixel data and properties of the loaded image
   * @throws IOException if there is an error reading the image file
   * @see Image
   */
  int[][][] loadImage() throws IOException;

  /**
   * Saves an {@code Image} object to a specified file path.
   *
   * <p>
   * This method is called by the model using the file path argument provided by the controller. The
   * pixel data from the {@code Image} object is saved in the appropriate image file format,
   * depending on the specific implementation of the {@code ImageHandler}.
   * </p>
   *
   * @param image the {@code Image} object containing the pixel data and properties to be saved
   * @throws IOException if there is an error writing the image file
   */
  void saveImage(int[][][] image) throws IOException;
}
