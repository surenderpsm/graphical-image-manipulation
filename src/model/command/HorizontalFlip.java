package model.command;

import model.Cache;
import utils.arguments.ArgumentWrapper;

/**
 * class representing the command that Flips an image horizontally .
 */
// horizontal flip deals with columns
class HorizontalFlip extends AbstractFlip {

  /**
   * Constructs a new HorizontalFlip processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */
  public HorizontalFlip(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Calculates the unchanged row index for the flipped image.
   *
   * @param currentRow The current row index
   * @return The transformed row index after flipping
   */

  @Override
  protected int getRowIndex(int currentRow) {
    return currentRow; // No change in row for horizontal flip
  }

  /**
   * Returns the new column index since horizontal flipping affects columns.
   *
   * @param currentCol The current column index
   * @return The same column index
   */
  @Override
  protected int getColIndex(int currentCol) {
    return width - 1 - currentCol; // Flip columns
  }

}
