package model.command;

import model.Image;

class BlueComponent extends AbstractColorComponent {

  public BlueComponent(String rawArguments) {
    super(rawArguments);
  }

  BlueComponent(Image image, String imageName) {
    super(image, imageName);
  }

  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[2] = currentImage.getBlueChannelData()[x][y];
    return pixel;
  }

}
