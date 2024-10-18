package model.command;

import static model.arg.ArgumentType.FILE_IN;
import static model.arg.ArgumentType.NEW_IMAGE;

import model.arg.ArgumentType;

class Load extends AbstractCommand {


  @Override
  public ArgumentType[] getArgumentTypes() {
    return new ArgumentType[]{FILE_IN, NEW_IMAGE};
  }

}
