package model.command;

//import java.lang.reflect.InvocationTargetException;

import model.Image;

abstract class AbstractCommand implements Command {


  protected final String[] args;
  protected Image currentImage;
  protected String imageName;

  AbstractCommand(String rawArguments) {
    args = rawArguments.split(" ");
  }

  protected String getArg(int argumentNumber) throws IndexOutOfBoundsException {
    return args[argumentNumber];
  }

  protected int numberOfArgs() {
    return args.length;
  }

}
