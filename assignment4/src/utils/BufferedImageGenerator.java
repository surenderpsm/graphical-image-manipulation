package utils;

import java.awt.image.BufferedImage;

public class BufferedImageGenerator {

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
  public static BufferedImage createBufferedImage(int[][][] pixelData) {
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
