package model.command;

import model.Cache;
import model.Image;

abstract class AbstractCommand implements Command {


  protected final String[] args;
  protected Image currentImage;
  protected String imageName;
  private int numberofArgs;

  protected final Cache cache;

  protected AbstractCommand(String rawArguments, Cache cache) {
    args = rawArguments.split(" ");
    numberofArgs = args.length;
    this.cache = cache;
  }

  /**
   * Used when Commands are used for other commands.
   */
  protected AbstractCommand(Image image, String imageName, Cache cache) {
    args = null;
    numberofArgs = 0;
    this.imageName = imageName;
    currentImage = image;
    this.cache = cache;
  }

  protected String getArg(int argumentNumber) throws IndexOutOfBoundsException {
    return args[argumentNumber];
  }
  protected void setNumberofArgs(int numberofArgs) {
    this.numberofArgs = numberofArgs;
  }
  protected int numberOfArgs() {
    return numberofArgs;
  }

  protected int parseInt(int argumentNumber, int min, int max) {
    try {
      return intValidation(Integer.parseInt(getArg(argumentNumber)), min, max);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Expected integer argument at position " + argumentNumber + ".");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "Expected " + e.getMessage() + " at position " + argumentNumber + ".");
    }
  }

  protected int parseInt(int argumentNumber) {
    try {
      return Integer.parseInt(getArg(argumentNumber));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Expected integer argument at position " + argumentNumber + ".");
    }
  }

  private int intValidation(int value, int min, int max) {
    if (value < min || value > max) {
      throw new IllegalArgumentException("value must be between " +min +" and " + max);
    }
    return value;
  }
}
