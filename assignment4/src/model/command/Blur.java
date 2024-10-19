package model.command;


import model.Image;

class Blur extends AbstractCommand {

  private final Image currentImage;
  private final String blurredImageName;

  public Blur(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(1));
    blurredImageName = getArg(2);
  }

  @Override
  protected boolean run() {
    return false;
  }
}
