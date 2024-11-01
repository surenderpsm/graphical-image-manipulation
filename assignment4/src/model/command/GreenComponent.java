package model.command;

class GreenComponent extends AbstractColorComponent {

  public GreenComponent(String rawArguments) {
    super(rawArguments);
  }

  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[1] = currentImage.getGreenChannelData()[x][y];
    return pixel;
  }

}
