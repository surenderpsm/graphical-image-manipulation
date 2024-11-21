package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The {@code HistogramGenerator} class is responsible for creating a graphical representation of an
 * image histogram, displaying the intensity of each color channel (Red, Green, and Blue). It
 * generates a visual representation of the histogram by drawing lines that represent the frequency
 * distribution of pixel intensities for each channel.
 *
 * <p>This class uses a 2D array for the histogram data and produces a {@code BufferedImage}
 * representing the histogram. The generated image includes a grid, axes, and individual color lines
 * for each channel.</p>
 *
 * <h2>Example Usage:</h2>
 * <pre>
 *     int[][] histogramData = ...;  // Populate histogram data
 *     HistogramGenerator generator = new HistogramGenerator(histogramData);
 *     int[][][] histogramImage = generator.getImage();
 * </pre>
 */
public class HistogramGenerator {

  // Histogram data for RGB channels
  private final int[][] histogram;
  // Size of the generated image (width and height)
  private static final int SIZE = 256;
  // Padding for drawing the histogram
  private static final int PADDING = 8;
  // Grid spacing for vertical and horizontal grid lines
  private static final int GRID_SPACING = 32;
  // Background color (white)
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  // Grid color (light gray)
  private static final Color GRID_COLOR = new Color(220, 220, 220);
  // Axes color (light gray)
  private static final Color AXES_COLOR = new Color(220, 220, 220);

  /**
   * Constructs a {@code HistogramGenerator} with the given histogram data.
   *
   * <p>The histogram data is expected to be a 2D array where each row represents a color channel
   * (0 for Red, 1 for Green, and 2 for Blue), and each column represents pixel intensity values
   * from 0 to 255 for that channel.</p>
   *
   * @param histogram A 2D array representing the histogram data for RGB channels.
   */
  public HistogramGenerator(int[][] histogram) {
    this.histogram = histogram;
  }

  /**
   * Generates an image representing the histogram of the provided data.
   *
   * <p>This method creates a {@code BufferedImage}, draws a grid, axes, and lines representing
   * the histograms for each RGB channel. The histogram lines are scaled based on the maximum value
   * for each channel.</p>
   *
   * <p>The image is returned as a 3D array where the first two dimensions represent pixel
   * coordinates (height and width), and the third dimension represents the RGB color channels.</p>
   *
   * @return A 3D array representing the image of the histogram.
   */
  public int[][][] getImage() {
    BufferedImage bufferedImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = bufferedImage.createGraphics();

    // Fill background
    g.setColor(BACKGROUND_COLOR);
    g.fillRect(0, 0, SIZE, SIZE);

    // Draw grid
    drawGrid(g);

    // Draw histogram lines for each channel
    for (int channel = 0; channel < histogram.length; channel++) {
      int maxValue = 0;
      for (int value : histogram[channel]) {
        maxValue = Math.max(maxValue, value);
      }

      if (maxValue > 0) { // Avoid division by zero
        // Choose color based on channel (Red, Green, Blue)
        Color
            lineColor =
            new Color(channel == 0 ? 255 : 0, channel == 1 ? 255 : 0, channel == 2 ? 255 : 0);

        // Draw histogram line
        int prevX = PADDING;
        int prevY = SIZE - PADDING;

        for (int i = 0; i < histogram[channel].length; i++) {
          int x = PADDING + (i * (SIZE - 2 * PADDING) / 255);
          int
              barHeight =
              (int) ((histogram[channel][i] * (SIZE - 2 * PADDING)) / (double) maxValue);
          int y = SIZE - PADDING - barHeight;

          // Ensure y is within bounds
          y = Math.max(PADDING, Math.min(y, SIZE - PADDING));

          // Draw line for the histogram
          g.setColor(lineColor);
          g.drawLine(prevX, prevY, x, y);

          // Update previous point
          prevX = x;
          prevY = y;
        }
      }
    }

    // Draw axes
    drawAxes(g);
    g.dispose();

    // Convert BufferedImage to int[][][] format for easier access
    return convertTo3DArray(bufferedImage);
  }

  /**
   * Draws the grid on the image.
   *
   * <p>Vertical and horizontal grid lines are drawn at intervals of {@code GRID_SPACING},
   * providing a visual reference for the histogram values.</p>
   *
   * @param g The {@code Graphics2D} object used to draw on the image.
   */
  private void drawGrid(Graphics2D g) {
    g.setColor(GRID_COLOR);

    // Draw vertical grid lines
    for (int x = PADDING; x < SIZE - PADDING; x += GRID_SPACING) {
      g.drawLine(x, PADDING, x, SIZE - PADDING);
    }

    // Draw horizontal grid lines
    for (int y = PADDING; y < SIZE - PADDING; y += GRID_SPACING) {
      g.drawLine(PADDING, y, SIZE - PADDING, y);
    }
  }

  /**
   * Draws the axes on the image.
   *
   * <p>The Y-axis is drawn on the left, and the X-axis is drawn at the bottom of the image.
   * Both axes are used as references for the histogram data.</p>
   *
   * @param g The {@code Graphics2D} object used to draw on the image.
   */
  private void drawAxes(Graphics2D g) {
    g.setColor(AXES_COLOR);

    // Draw Y-axis
    g.drawLine(PADDING, PADDING, PADDING, SIZE - PADDING);
    // Draw X-axis
    g.drawLine(PADDING, SIZE - PADDING, SIZE - PADDING, SIZE - PADDING);
  }

  /**
   * Converts the {@code BufferedImage} into a 3D array of RGB values.
   *
   * <p>This method extracts the RGB values of each pixel from the image and stores them in a 3D
   * array. The array structure is [height][width][RGB channels], where each color channel is
   * represented as an integer value (0-255) for Red, Green, and Blue.</p>
   *
   * @param bufferedImage The {@code BufferedImage} to be converted.
   * @return A 3D array representing the image's RGB values.
   */
  private int[][][] convertTo3DArray(BufferedImage bufferedImage) {
    int[][][] imageArray = new int[SIZE][SIZE][3];
    for (int y = 0; y < SIZE; y++) {
      for (int x = 0; x < SIZE; x++) {
        int rgb = bufferedImage.getRGB(x, y);
        imageArray[y][x][0] = (rgb >> 16) & 0xFF; // Red
        imageArray[y][x][1] = (rgb >> 8) & 0xFF;  // Green
        imageArray[y][x][2] = rgb & 0xFF;         // Blue
      }
    }
    return imageArray;
  }
}
