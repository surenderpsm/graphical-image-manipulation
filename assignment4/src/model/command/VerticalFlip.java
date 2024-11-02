package model.command;

import model.Cache;

class VerticalFlip extends AbstractFlip {

  public VerticalFlip(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  @Override
  protected int getRowIndex(int currentRow) {
    return height - 1 - currentRow; // Flip rows
  }

  @Override
  protected int getColIndex(int currentCol) {
    return currentCol; // No change in column for vertical flip
  }


}
