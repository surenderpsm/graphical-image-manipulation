package controller.imagehandler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The {@code CommonImageHandler} class provides a standard implementation for loading and saving
 * common image formats using the {@code ImageIO} class. It extends the {@code AbstractImageHandler}
 * and is responsible for handling images in formats that can be processed by Java's built-in image
 * IO support.
 *
 * <p>
 * This class can handle images with standard RGB channels (red, green, blue), where each pixel is
 * represented as an integer value containing RGB values. The {@code loadImage()} method reads the
 * image from the file, and the {@code saveImage()} method saves it back to the file in the
 * specified format.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 *   // For example, loading and saving a PNG image
 *   CommonImageHandler handler = new CommonImageHandler("path/to/image.png", "png");
 *   int[][][] pixelData = handler.loadImage();  // Load the image into a 3D array
 *   handler.saveImage(pixelData);              // Save the image from the 3D array
 * </pre>
 */
class CommonImageHandler extends AbstractImageHandler {

  /**
   * Constructs a {@code CommonImageHandler} for the specified file path and image extension.
   *
   * @param path      the path to the image file
   * @param extension the file extension (e.g., "png", "jpg", "bmp")
   */
  CommonImageHandler(String path, String extension) {
    super(path, extension);
  }

  /**
   * Loads the image from the specified file and returns its pixel data in a 3D array. The array
   * format is [row][column][RGB], where each RGB value is stored as an integer.
   *
   * <p>
   * This method uses Java's {@code ImageIO} to read the image file and then extracts the RGB values
   * of each pixel, storing them in a 3D array.
   * </p>
   *
   * @return a 3D array containing the RGB values of each pixel in the image
   * @throws IOException if there is an error reading the image file
   */
  public int[][][] loadImage() throws IOException {
    BufferedImage image = ImageIO.read(getPath());

    int width = image.getWidth();
    int height = image.getHeight();

    // 3D array to store [row][column][RGB] values
    int[][][] pixelData = new int[height][width][3];

    // Iterate over each pixel and extract RGB values
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = image.getRGB(x, y); // Get pixel value as a single integer

        // Extract individual red, green, blue values
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Store RGB values in the 3D array
        pixelData[y][x][0] = red;
        pixelData[y][x][1] = green;
        pixelData[y][x][2] = blue;
      }
    }
    return pixelData;
  }

  /**
   * Saves the image represented by the given pixel data to the file specified in the path.
   *
   * <p>
   * This method uses Java's {@code ImageIO} to write the pixel data to a file in the specified
   * format. The pixel data is converted from a 3D array to a {@code BufferedImage} before saving.
   * </p>
   *
   * @param pixelData the 3D array containing the RGB values of the image
   * @throws IOException if there is an error writing the image file
   */
  @Override
  public void saveImage(int[][][] pixelData) throws IOException {
    BufferedImage image = createBufferedImage(pixelData);

    // Write the image to the file in the specified format
    ImageIO.write(image, getExtension(), getPath());
  }

  /**
   * Converts the pixel data in the 3D array to a {@code BufferedImage}.
   *
   * <p>
   * This method creates a {@code BufferedImage} from the provided 3D array, where each element in
   * the array corresponds to the RGB values of a single pixel in the image.
   * </p>
   *
   * @param pixelData the 3D array containing the RGB values of the image
   * @return a {@code BufferedImage} representing the image
   */
  private BufferedImage createBufferedImage(int[][][] pixelData) {
    int height = pixelData.length;
    int width = pixelData[0].length;
    int num_channels = pixelData[0][0].length;

    // Determine the appropriate BufferedImage type based on the number of channels
    var type = (num_channels == 3) ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_4BYTE_ABGR;

    BufferedImage image = new BufferedImage(width, height, type);

    // Iterate over each pixel and set the RGB value in the BufferedImage
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = pixelData[y][x][0];  // Red value
        int g = pixelData[y][x][1];  // Green value
        int b = pixelData[y][x][2];  // Blue value

        // Combine RGB values into a single integer and set it to the BufferedImage
        int rgb = (r << 16) | (g << 8) | b;  // Convert to RGB integer
        image.setRGB(x, y, rgb);
      }
    }

    return image;
  }
}
