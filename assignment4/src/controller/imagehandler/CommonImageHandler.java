package controller.imagehandler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class CommonImageHandler extends AbstractImageHandler {

  CommonImageHandler(String path, String extension) {
    super(path, extension);
  }

  public int[][][] loadImage() throws IOException {
    BufferedImage image = ImageIO.read(getPath());

    int width = image.getWidth();
    int height = image.getHeight();

    // 3D array to store [row][column][RGB] values
    int[][][] pixelData = new int[height][width][3];

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

  @Override
  public void saveImage(int[][][] pixelData) throws IOException {
    BufferedImage image = createBufferedImage(pixelData);

    ImageIO.write(image, getExtension(), getPath());
  }

  private BufferedImage createBufferedImage(int[][][] pixelData) {
    int height = pixelData.length;
    int width = pixelData[0].length;
    int num_channels = pixelData[0][0].length;

    var type = (num_channels == 3) ? BufferedImage.TYPE_3BYTE_BGR : BufferedImage.TYPE_4BYTE_ABGR;

    BufferedImage image = new BufferedImage(width, height, type);

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
