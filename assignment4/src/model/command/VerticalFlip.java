package model.command;

import model.Cache;

/**
 * Flips an image vertically (top to bottom). This processor mirrors the image along its horizontal
 * axis, resulting in an upside-down version of the original image.
 */

class VerticalFlip extends AbstractFlip {

  /**
   * Constructs a new VerticalFlip processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */
  public VerticalFlip(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Calculates the new row index for the flipped image.
   *
   * @param currentRow The current row index
   * @return The transformed row index after flipping
   */

  @Override
  protected int getRowIndex(int currentRow) {
    return height - 1 - currentRow; // Flip rows
  }

  /**
   * Returns the unchanged column index since vertical flipping doesn't affect columns.
   *
   * @param currentCol The current column index
   * @return The same column index
   */
  @Override
  protected int getColIndex(int currentCol) {
    return currentCol; // No change in column for vertical flip
  }


}
