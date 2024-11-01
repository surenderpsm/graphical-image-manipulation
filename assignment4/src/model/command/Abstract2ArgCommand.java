package model.command;

import model.Image;

/**
 * An abstract class for all commands with 2 arguments where first argument is an Image and the
 * second is the resulting image name.
 */
abstract class Abstract2ArgCommand extends AbstractCommand {

  Abstract2ArgCommand(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = Image.Cache.get(getArg(0));
    imageName = getArg(1);
  }

  Abstract2ArgCommand(Image image, String imageName) {
    super();
    currentImage = image;
    this.imageName = imageName;
  }
}
