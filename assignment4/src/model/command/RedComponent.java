package model.command;

class RedComponent extends AbstractColorComponent {

  public RedComponent(String rawArguments) {
    super(rawArguments);
  }

  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[0] = currentImage.getRedChannelData()[x][y];
    return pixel;
  }

}
