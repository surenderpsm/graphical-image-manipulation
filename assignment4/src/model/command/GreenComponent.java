package model.command;

import model.Cache;
import model.Image;

class GreenComponent extends AbstractColorComponent {

  public GreenComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  GreenComponent(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
  }

  @Override
  protected int[] getPixel(int x, int y) {
    int[] pixel = new int[3];
    pixel[1] = currentImage.getGreenChannelData()[x][y];
    return pixel;
  }

}
