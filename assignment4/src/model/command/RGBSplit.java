package model.command;


import model.Image;

class RGBSplit extends AbstractCommand {

  private final String redImageName;
  private final String greenImageName;
  private final String blueImageName;

  public RGBSplit(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 4 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    redImageName = getArg(1);
    greenImageName = getArg(2);
    blueImageName = getArg(3);
  }

  public void execute() {
    new RedComponent(currentImage, redImageName).execute();
    new BlueComponent(currentImage, blueImageName).execute();
    new GreenComponent(currentImage, greenImageName).execute();
  }

}
