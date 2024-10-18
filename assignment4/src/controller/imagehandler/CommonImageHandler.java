package controller.imagehandler;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Image;

class CommonImageHandler extends AbstractImageHandler {

  CommonImageHandler(String path) throws FileNotFoundException {
    super(path);
  }

  public void loadImage() {
    BufferedImage image;
    try {
      image = ImageIO.read(getPath());
    } catch (IOException e) {
      System.out.println("Error loading image " + getPath());
      return;
    }

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
    setImage(new Image(pixelData));
  }
}
