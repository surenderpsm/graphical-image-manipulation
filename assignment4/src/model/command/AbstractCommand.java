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

}
