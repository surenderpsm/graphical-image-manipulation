package model.command;

import model.Cache;
import model.Image;

class RedComponent extends AbstractColorComponent {

  public RedComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  RedComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[0] = currentImage.getRedChannelData()[x][y];
    return pixel;
  }

}
