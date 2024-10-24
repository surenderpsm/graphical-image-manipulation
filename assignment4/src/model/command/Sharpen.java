package model.command;


import model.Image;

class Sharpen extends AbstractCommand {

  public Sharpen(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    imageName = getArg(1);
  }

  public void execute() {

    int height = currentImage.getHeight();
    int width = currentImage.getWidth();
    int[][][] imageArray = new int[height][width][3];
    double[][] filter = {
            {-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125,  0.25,   0.25,   0.25,  -0.125},
            {-0.125,  0.25,   1.0,    0.25,  -0.125},
            {-0.125,  0.25,   0.25,   0.25,  -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}
    };
    Filter filterInstance = new Filter();
    imageArray = filterInstance.filterImage(currentImage, filter);
    Image sharpenedImage = new Image(imageArray);
    Image.Cache.set(imageName, sharpenedImage);

  }

}
