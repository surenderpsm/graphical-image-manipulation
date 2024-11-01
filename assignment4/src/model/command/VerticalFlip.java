package model.command;

class VerticalFlip extends AbstractFlip {

  public VerticalFlip(String rawArguments) {
    super(rawArguments);
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
