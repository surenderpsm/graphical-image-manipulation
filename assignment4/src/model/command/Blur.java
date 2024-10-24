package model.command;


import model.Image;

class Blur extends AbstractCommand {

  public Blur(String rawArguments) {
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
    double[][] filter = {{1 / 16.0, 2 / 16.0, 1 / 16.0}, {2 / 16.0, 4 / 16.0, 2 / 16.0},
        {1 / 16.0, 2 / 16.0, 1 / 16.0}};
    Filter F = new Filter();
    imageArray = F.filterImage(currentImage, filter);
    Image blurredImage = new Image(imageArray);
    Image.Cache.set(imageName, blurredImage);

  }

}
