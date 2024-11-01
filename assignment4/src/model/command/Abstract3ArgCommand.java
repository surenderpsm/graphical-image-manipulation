package model.command;

import model.Image;

/**
 * An abstract class where the command has 3 arguments: first is an integer, 2nd is an Image and 3rd
 * is the resulting image name.
 */
abstract class Abstract3ArgCommand extends AbstractCommand {

  protected final int value;

  Abstract3ArgCommand(String rawArguments) {
    super(rawArguments);
    if (numberOfArgs() != 3) {
      throw new IllegalArgumentException("Expected 3 arguments.");
    }
    try {
      value = Integer.parseInt(getArg(0));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Expected integer argument at position 0.");
    }
    currentImage = Image.Cache.get(getArg(1));
    imageName = getArg(2);
  }
}
