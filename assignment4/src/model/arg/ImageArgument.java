package model.arg;

import model.Image;

public class ImageArgument implements Argument {

  private final Image value;

  public ImageArgument(Image value) {
    this.value = value;
  }

  @Override
  public Image getValue() {
    return value;
  }
}
