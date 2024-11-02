package model.command;


import model.Cache;

class RGBSplit extends AbstractCommand {

  private final String redImageName;
  private final String greenImageName;
  private final String blueImageName;

  public RGBSplit(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 4 arguments.");
    }
    currentImage = cache.get(getArg(0));
    redImageName = getArg(1);
    greenImageName = getArg(2);
    blueImageName = getArg(3);
  }

  public void execute() {
    new RedComponent(currentImage, redImageName, cache).execute();
    new BlueComponent(currentImage, blueImageName, cache).execute();
    new GreenComponent(currentImage, greenImageName, cache).execute();
  }

}
