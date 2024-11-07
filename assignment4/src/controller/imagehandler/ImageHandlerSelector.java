package controller.imagehandler;

import java.lang.reflect.InvocationTargetException;

/**
 * The {@code ImageHandlerSelector} enum is responsible for selecting the appropriate image handler
 * based on the file format (extension). Each enum constant corresponds to a specific image format
 * and its associated handler class that can process that format (e.g., PNG, JPG, PPM).
 *
 * <p>
 * This enum ensures that the right {@code ImageHandler} is chosen based on the file extension of
 * the image path. The {@code createImageHandler} method instantiates the correct handler using
 * reflection, enabling the controller to load or save images in various formats dynamically.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 *   // For a file path with ".png" extension, the PNG handler is selected
 *   ImageHandler handler = ImageHandlerSelector.PNG.createImageHandler("path/to/image.png");
 *   int[][][] pixelData = handler.loadImage();  // Load the image into a 3D array
 *   handler.saveImage(pixelData);              // Save the image from the 3D array
 * </pre>
 */
public enum ImageHandlerSelector {
  PPM("ppm", PPMHandler.class),
  PNG("png", CommonImageHandler.class),
  JPG("jpg", CommonImageHandler.class),
  JPEG("jpeg", CommonImageHandler.class),
  ;

  private final String format;
  private final Class<? extends ImageHandler> handlerClass;

  /**
   * Constructor for initializing the enum constants with a format and handler class.
   *
   * @param format       the image format (e.g., "png", "jpg", "ppm")
   * @param handlerClass the {@code ImageHandler} class that handles the specified format
   */
  ImageHandlerSelector(String format, Class<? extends ImageHandler> handlerClass) {
    this.format = format;
    this.handlerClass = handlerClass;
  }

  /**
   * Gets the format supported by this enum constant.
   *
   * @return the format string (e.g., "png", "jpg", "ppm")
   */
  public String getSupportingFormat() {
    return format;
  }

  /**
   * Checks if the current enum constant supports the given file path based on its extension.
   *
   * @param path the file path to check
   * @return true if the file extension matches the supported format, false otherwise
   */
  public boolean isMatchingImageHandler(String path) {
    return parseFormat(path).equals(this.getSupportingFormat());
  }

  /**
   * Creates an instance of the {@code ImageHandler} for the given file path.
   *
   * <p>
   * This method uses reflection to instantiate the handler class for the corresponding image
   * format. The handler class is selected based on the file extension.
   * </p>
   *
   * @param path the path of the image file for which the handler is to be created
   * @return an instance of the appropriate {@code ImageHandler} for the image format
   */
  public ImageHandler createImageHandler(String path) {
    return this.instantiateCommand(path);
  }

  /**
   * Dynamically instantiates the handler class using reflection.
   *
   * <p>
   * This method uses reflection to invoke the constructor of the handler class, passing the image
   * file path and the supported format as parameters.
   * </p>
   *
   * @param path the image file path
   * @return an instance of the corresponding {@code ImageHandler}
   * @throws InternalError if there is an error during instantiation or reflection
   */
  private ImageHandler instantiateCommand(String path) {
    try {
      // Instantiating the handler class using the file path and format
      return handlerClass.getDeclaredConstructor(String.class, String.class)
          .newInstance(path, getSupportingFormat());
    } catch (NoSuchMethodException e) {
      throw new InternalError(
          "Internal Error: The specified format " + format + " has no String constructor.");
    } catch (InvocationTargetException e) {
      throw new UnsupportedOperationException(
          "Internal Error: There was an error in constructor invocation of handler  : "
              + e.getCause().getMessage());
    } catch (InstantiationException e) {
      throw new InternalError("Internal Error: The specified handler cannot be instantiated.");
    } catch (IllegalAccessException e) {
      throw new InternalError("Internal Error: The specified handler cannot be accessed.");
    }
  }

  /**
   * Parses the file extension from the given file path.
   *
   * <p>
   * This method extracts the file extension from the provided path string to determine the image
   * format.
   * </p>
   *
   * @param path the file path to extract the format from
   * @return the file extension (e.g., "png", "jpg", "ppm")
   * @throws UnsupportedOperationException if the format is invalid or not supported
   */
  private String parseFormat(String path) {
    String extension;
    int index = path.lastIndexOf('.');
    if (index > 0) {
      extension = path.substring(index + 1);
    }
    else {
      throw new UnsupportedOperationException("Format is illegal.");
    }
    return extension;
  }
}
