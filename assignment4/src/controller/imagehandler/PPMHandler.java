package controller.imagehandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class PPMHandler extends AbstractImageHandler {

  PPMHandler(String path, String extension) {
    super(path, extension);
  }

  @Override
  public int[][][] loadImage() throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(getPath()));
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][][] image = new int[width][height][3];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // if values greater than max value, then clamp at maxValue.
        int r = sc.nextInt();
        if (r > maxValue) {
          maxValue = r;
        }
        image[i][j][0] = r;
        int g = sc.nextInt();
        if (g > maxValue) {
          maxValue = g;
        }
        image[i][j][1] = g;
        int b = sc.nextInt();
        if (b > maxValue) {
          maxValue = b;
        }
        image[i][j][2] = b;
      }
    }

    return image;
  }

  @Override
  public void saveImage(int[][][] image) throws IOException {
    int height = image.length;
    int width = image[0].length;

    // Create a FileWriter object to write to the file
    FileWriter writer = new FileWriter(getPath());

    // Write the PPM header
    writer.write("P3\n");  // Magic number
    writer.write(width + " " + height + "\n");  // Image width and height
    writer.write("255\n");  // Maximum color value (255)

    // Write the pixel data
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = image[i][j][0];  // Red value
        int g = image[i][j][1];  // Green value
        int b = image[i][j][2];  // Blue value
        writer.write(r + " " + g + " " + b + " ");
      }
      writer.write("\n");  // Newline after each row
    }

    // Close the file
    writer.close();
  }
}

