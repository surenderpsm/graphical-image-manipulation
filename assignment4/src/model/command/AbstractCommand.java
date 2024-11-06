package model.command;

//import java.lang.reflect.InvocationTargetException;

import model.Cache;
import model.Image;

abstract class AbstractCommand implements Command {


  protected final String[] args;
  protected Image currentImage;
  protected String imageName;

  protected final Cache cache;

  protected AbstractCommand(String rawArguments, Cache cache) {
    args = rawArguments.split(" ");
    this.cache = cache;
  }

  /**
   * Used when Commands are used for other commands.
   */
  protected AbstractCommand(Cache cache) {
    args = null;
    this.cache = cache;
  }

  protected String getArg(int argumentNumber) throws IndexOutOfBoundsException {
    return args[argumentNumber];
  }

  protected int numberOfArgs() {
    return args.length;
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
