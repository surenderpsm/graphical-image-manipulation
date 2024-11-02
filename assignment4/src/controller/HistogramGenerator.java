package controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class HistogramGenerator {

  private final int[][] histogram;
  private static final int SIZE = 256;
  private static final int PADDING = 8;
  private static final int GRID_SPACING = 32; // Creates 8 grid sections (256/32 = 8)
  // Colors for background, grid, and axes
  private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
  private static final Color GRID_COLOR = new Color(220, 220, 220);
  private static final Color AXES_COLOR = new Color(220, 220, 220);

  public HistogramGenerator(int[][] histogram) {
    this.histogram = histogram;
  }

  public int[][][] getImage() {
    // Create a BufferedImage
    BufferedImage bufferedImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = bufferedImage.createGraphics();

    // Fill background
    g.setColor(BACKGROUND_COLOR);
    g.fillRect(0, 0, SIZE, SIZE);

    // Draw grid
    drawGrid(g);

    // Draw histogram lines for each channel
    for (int channel = 0; channel < histogram.length; channel++) {
      // Find the maximum value in the histogram for scaling
      int maxValue = 0;
      for (int value : histogram[channel]) {
        maxValue = Math.max(maxValue, value);
      }

      if (maxValue > 0) { // Avoid division by zero
        // Color for the current channel
        Color lineColor = new Color(channel == 0 ? 255 : 0, channel == 1 ? 255 : 0,
            channel == 2 ? 255 : 0);

        // Previous point for line drawing
        int prevX = PADDING;
        int prevY = SIZE - PADDING;

        // Draw histogram line
        for (int i = 0; i < histogram[channel].length; i++) {
          // Calculate current point
          int x = PADDING + (i * (SIZE - 2 * PADDING) / 255);
          int barHeight = (int) ((histogram[channel][i] * (SIZE - 2 * PADDING))
              / (double) maxValue);
          int y = SIZE - PADDING - barHeight;

          // Ensure y is within bounds
          y = Math.max(PADDING, Math.min(y, SIZE - PADDING));

          // Draw line from previous point to current point
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
    // Convert BufferedImage to int[][][] format
    return convertTo3DArray(bufferedImage);
  }

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

  private void drawAxes(Graphics2D g) {
    g.setColor(AXES_COLOR);

    // Draw Y-axis
    g.drawLine(PADDING, PADDING, PADDING, SIZE - PADDING);
    // Draw X-axis
    g.drawLine(PADDING, SIZE - PADDING, SIZE - PADDING, SIZE - PADDING);
  }

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
