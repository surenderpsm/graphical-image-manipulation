package controller.imagehandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code PPMHandler} class is responsible for handling PPM (Portable Pixel Map) image files.
 * It provides functionality to load and save PPM images using the "P3" text format, which stores
 * pixel values in a human-readable format.
 *
 * <p>
 * The {@code loadImage} method reads an image from a PPM file and returns the pixel data as a 3D
 * array, where each pixel is represented by an RGB triplet. The {@code saveImage} method writes
 * pixel data to a PPM file in the "P3" format, which includes the header information and the pixel values.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 *   // To load a PPM image
 *   PPMHandler ppmHandler = new PPMHandler("path/to/image.ppm", "ppm");
 *   int[][][] pixelData = ppmHandler.loadImage();  // Load the image into a 3D array
 *
 *   // To save a PPM image
 *   ppmHandler.saveImage(pixelData);  // Save the image from the 3D array
 * </pre>
 */
class PPMHandler extends AbstractImageHandler {

  /**
   * Constructor that initializes the path and extension for the PPM file.
   *
   * @param path      the file path of the PPM image
   * @param extension the file extension (should be "ppm")
   */
  PPMHandler(String path, String extension) {
    super(path, extension);
  }

  /**
   * Loads a PPM image from the file system.
   *
   * <p>
   * This method reads the PPM image file, ignoring any comment lines (lines starting with '#') and
   * extracting the image's width, height, and RGB pixel values. It returns the image as a 3D array of
   * integers, where each pixel is represented by three integers for the red, green, and blue components.
   * </p>
   *
   * @return a 3D array containing the RGB values of the image
   * @throws FileNotFoundException if the PPM file cannot be found
   * @throws IllegalArgumentException if the file is not a valid PPM file
   */
  @Override
  public int[][][] loadImage() throws FileNotFoundException {
    Scanner sc = new Scanner(new FileInputStream(getPath()));
    StringBuilder builder = new StringBuilder();

    // Read the file line by line, ignoring comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    // Set up the scanner to read from the string built without comments
    sc = new Scanner(builder.toString());

    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int[][][] image = new int[width][height][3];

    // Read pixel data and store it in the 3D array
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
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

  /**
   * Saves an image to a PPM file.
   *
   * <p>
   * This method writes the pixel data to a PPM file in the "P3" format, which includes the width,
   * height, maximum color value (255), and the RGB values for each pixel.
   * </p>
   *
   * @param image a 3D array representing the RGB pixel data to be saved
   * @throws IOException if there is an error writing to the PPM file
   */
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

    // Close the file writer
    writer.close();
  }
}
