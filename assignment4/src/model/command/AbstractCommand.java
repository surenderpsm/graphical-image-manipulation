package model.command;

import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;
import utils.arguments.OptionalArgumentKeyword;

/**
 * Abstract base class for all command operations in the image processing system. Provides common
 * functionality for argument parsing, validation, and command execution.
 */

abstract class AbstractCommand implements Command {


  protected final ArgumentWrapper args;
  protected Image currentImage;
  protected String imageName;
  private int numberOfArgs;

  protected final Cache cache;

  /**
   * constructor of this class.
   *
   * @param rawArguments string arguments input.
   * @param cache        cache object.
   */

  protected AbstractCommand(ArgumentWrapper rawArguments, Cache cache) {
    args = rawArguments;
    numberOfArgs = args.length();
    this.cache = cache;
  }

  /**
   * Alternative constructor for when commands are used within other commands.
   *
   * @param image     The source image to process
   * @param imageName The name to store the result under
   * @param cache     The cache for storing and retrieving images
   */
  protected AbstractCommand(Image image, String imageName, Cache cache) {
    args = null;
    numberOfArgs = 0;
    this.imageName = imageName;
    currentImage = image;
    this.cache = cache;
  }

  /**
   * Retrieves an argument at the specified index.
   *
   * @param argumentNumber the index of the argument required.
   * @return the string argument.
   */
  protected String parseString(int argumentNumber) {
    try {
      return args.getString(argumentNumber);
    } catch (IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Insufficient arguments.");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * to set the number of arguments.
   *
   * @param numberOfArgs no. of the arguments.
   */
  protected void setNumberOfArgs(int numberOfArgs) {
    this.numberOfArgs = numberOfArgs;
  }

  /**
   * to get the number of arguments.
   *
   * @return no. of arguments.
   */
  protected int numberOfArgs() {
    return numberOfArgs;
  }

  /**
   * Parses and validates an integer argument within specified bounds.
   *
   * @param argumentNumber argument index.
   * @param min            value required for the arg.
   * @param max            value required for the arg.
   * @return integer argument.
   */
  protected int parseInt(int argumentNumber, int min, int max) {
    try {
      return intValidation(args.getIntArgument(argumentNumber), min, max);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Expected integer argument at position " + argumentNumber + ".");
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "Expected " + e.getMessage() + " at position " + argumentNumber + ".");
    }
  }
  protected int parseInt(OptionalArgumentKeyword key, int min, int max) {
      return intValidation(args.getIntArgument(key), min, max);
  }

  /**
   * to parse integer arguments.
   *
   * @param argumentNumber argument index.
   * @return the integer argument.
   */
  protected int parseInt(int argumentNumber) {
    try {
      return args.getIntArgument(argumentNumber);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Expected integer argument at position " + argumentNumber + ".");
    }
  }


  /**
   * validate the integer argument. if the value out of bounds then throw error.
   *
   * @param value of the integer argument.
   * @param min   bound of the arg.
   * @param max   bound of the arg.
   * @return value of the argument if it doesn't throw bounds error.
   */
  private int intValidation(int value, int min, int max) {
    if (value < min || value > max) {
      throw new IllegalArgumentException("value must be between " + min + " and " + max);
    }
    return value;
  }
}
