package model.command;

import model.Cache;
import model.Image;

/**
 * An abstract class for all commands with 2 arguments where first argument is an Image and the
 * second is the resulting image name.
 */
abstract class Abstract2ArgCommand extends AbstractCommand {

  protected final int height;
  protected final int width;

  protected Abstract2ArgCommand(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(getArg(0));
    imageName = getArg(1);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
  }

  protected Abstract2ArgCommand(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
  }
}
