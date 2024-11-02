package model.command;

import model.Cache;
import model.Image;

class BlueComponent extends AbstractColorComponent {

  public BlueComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  BlueComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[2] = currentImage.getBlueChannelData()[x][y];
    return pixel;
  }

}
