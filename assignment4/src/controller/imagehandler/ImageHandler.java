package controller.imagehandler;

import java.io.IOException;
import model.Image;

/**
 * The {@code ImageHandler} interface provides an abstraction for loading and saving images.
 * Implementations of this interface handle the specific image file formats and are located in the
 * controller package. This interface is used by commands to load or save image data.
 *
 * <p>
 * During argument handling in the controller, the necessary file paths are passed to the methods
 * for loading or saving the images.
 * </p>
 *
 * <p>
 * The controller is expected to define logic that gets an ImageHandler based on the file extension
 * in the path.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 *   // in the controller (during argument handling). Assume args is passed to model.
 *   args.set(1, getMatchingImageHandler(".././koala.png"));  // This could call PNGImageHandler.
 *   args.set(2, getMatchingImageHandler("../koala.ppm"));    // This could call a PPMImageHandler.
 *
 *   // in the model
 *   Image image = args.get(1).loadImage();
 *   args.get(2).saveImage();
 * </pre>
 * <p>
 * From the usage example, it is evident that the model cannot edit an ImageHandler. They can only
 * run the public methods. Hence the controller is expected to supply the path to the ImageHandler
 * object during argument handling.
 * </p>
 */
public interface ImageHandler {

  /**
   * Loads an image from a specified file path.
   *
   * <p>
   * This method is called by the model using the file path argument passed by the controller. The
   * image data is returned in an {@code Image} object, which contains pixel data for all channels
   * (r, g, b, a) and the dimensions (width, height) of the image.
   * </p>
   *
   * @return an {@code Image} object containing the pixel data and image properties
   * @throws IOException if there is an error reading the image file
   * @see Image
   */
  int[][][] loadImage() throws IOException;

  /**
   * Saves the given {@code Image} object to a specified file path.
   *
   * <p>
   * This method is called by the model using the file path argument passed by the controller. The
   * image data from the {@code Image} object is saved to the appropriate file format, depending on
   * the implementation of this interface.
   * </p>
   *
   * @param image the {@code Image} object containing the pixel data and image properties to be
   *              saved
   * @throws IOException if there is an error writing the image file
   */
  void saveImage(int[][][] image) throws IOException;

}
