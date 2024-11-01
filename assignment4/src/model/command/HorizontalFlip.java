package model.command;

// horizontal flip deals with columns
class HorizontalFlip extends AbstractFlip {

  public HorizontalFlip(String rawArguments) {
    super(rawArguments);
  }

  @Override
  protected int getRowIndex(int currentRow) {
    return currentRow; // No change in row for horizontal flip
  }

  @Override
  protected int getColIndex(int currentCol) {
    return width - 1 - currentCol; // Flip columns
  }

}
