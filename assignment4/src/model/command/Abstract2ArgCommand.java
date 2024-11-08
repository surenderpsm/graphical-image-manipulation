package model.command;

import model.Cache;
import model.Image;

/**
 * An abstract class for all commands with 2 arguments where first argument is an Image and the.
 * second is the resulting image name.
 */
abstract class Abstract2ArgCommand extends AbstractCommand {

  protected final int height;
  protected final int width;

  /**
   * constructor for this class.
   *
   * @param rawArguments arguments of the commands from the txt file or command line.
   * @param cache        that stores our images for this user's session.
   */

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

  /**
   * constructor overloaded.
   *
   * @param image     image object.
   * @param imageName image name.
   * @param cache     cache object.
   */
  protected Abstract2ArgCommand(Image image, String imageName, Cache cache) {
    super(image, imageName, cache);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
  }
}
