package model.command;

import model.Image;

class RedComponent extends AbstractColorComponent {

  public RedComponent(String rawArguments) {
    super(rawArguments);
  }
  RedComponent(Image image, String imageName) {
    super(image, imageName);
  }

  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[0] = currentImage.getRedChannelData()[x][y];
    return pixel;
  }

}
