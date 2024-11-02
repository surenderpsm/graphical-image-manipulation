package controller;
public class HistogramGenerator {
  private final int[][] histogram;
  private static final int SIZE = 256;
  private static final int PADDING = 5;
  private static final int GRID_SPACING = 16; // Creates 8 grid sections (256/32 = 8)
  // RGB values for background (white)
  private static final int[] BACKGROUND_COLOR = {255, 255, 255};
  // RGB values for grid (light gray)
  private static final int[] GRID_COLOR = {220, 220, 220};
  // RGB values for axes (dark gray)
  private static final int[] AXES_COLOR = {80, 80, 80};

  public HistogramGenerator(int[][] histogram) {
    this.histogram = histogram;
  }

  public int[][][] getImage() {
    // Create 3D array [height][width][RGB]
    int[][][] image = new int[SIZE][SIZE][3];

    // Fill background
    for (int y = 0; y < SIZE; y++) {
      for (int x = 0; x < SIZE; x++) {
        image[y][x] = BACKGROUND_COLOR.clone();
      }
    }

    // Draw grid
    drawGrid(image);

    // Calculate the effective width for plotting
    int plotWidth = SIZE - (2 * PADDING);
    int plotHeight = SIZE - (2 * PADDING);

    // Draw histogram lines for each channel
    for (int channel = 0; channel < histogram.length; channel++) {
      // Find the maximum value in the histogram for scaling
      int maxValue = 0;
      for (int value : histogram[channel]) {
        maxValue = Math.max(maxValue, value);
      }

      if (maxValue > 0) { // Avoid division by zero
        // Previous point for line drawing
        int prevX = PADDING;
        int prevY = SIZE - PADDING;

        // Create color array for current channel
        int[] lineColor = new int[]{0, 0, 0};
        lineColor[channel] = 255; // Set appropriate channel to 255

        // Draw histogram line
        for (int i = 0; i < histogram[channel].length; i++) {
          // Calculate current point
          int x = PADDING + (i * plotWidth / 255);
          int barHeight = (int)((histogram[channel][i] * plotHeight) / maxValue);
          int y = SIZE - PADDING - barHeight;

          // Ensure y is within bounds
          y = Math.max(PADDING, Math.min(y, SIZE - PADDING));

          // Draw line from previous point to current point
          drawLine(image, prevX, prevY, x, y, lineColor);

          // Update previous point
          prevX = x;
          prevY = y;
        }
      }
    }

    // Draw axes (on top of grid)
    drawAxes(image);

    return image;
  }

  private void drawLine(int[][][] image, int x1, int y1, int x2, int y2, int[] color) {
    // Bresenham's line algorithm
    int dx = Math.abs(x2 - x1);
    int dy = Math.abs(y2 - y1);
    int sx = x1 < x2 ? 1 : -1;
    int sy = y1 < y2 ? 1 : -1;
    int err = dx - dy;

    while (true) {
      if (y1 >= 0 && y1 < SIZE && x1 >= 0 && x1 < SIZE) {
        image[y1][x1] = color.clone();
      }

      if (x1 == x2 && y1 == y2) break;

      int e2 = 2 * err;
      if (e2 > -dy) {
        err -= dy;
        x1 += sx;
      }
      if (e2 < dx) {
        err += dx;
        y1 += sy;
      }
    }
  }

  private void drawGrid(int[][][] image) {
    // Draw vertical grid lines
    for (int x = PADDING; x < SIZE - PADDING; x += GRID_SPACING) {
      for (int y = PADDING; y < SIZE - PADDING; y++) {
        image[y][x] = GRID_COLOR.clone();
      }
    }

    // Draw horizontal grid lines
    for (int y = PADDING; y < SIZE - PADDING; y += GRID_SPACING) {
      for (int x = PADDING; x < SIZE - PADDING; x++) {
        image[y][x] = GRID_COLOR.clone();
      }
    }
  }

  private void drawAxes(int[][][] image) {
    // Draw Y-axis
    for (int y = PADDING; y < SIZE - PADDING; y++) {
      image[y][PADDING] = AXES_COLOR.clone();
    }
    // Draw X-axis
    for (int x = PADDING; x < SIZE - PADDING; x++) {
      image[SIZE - PADDING][x] = AXES_COLOR.clone();
    }
  }
}