package model.command;

import static model.arg.ArgumentType.EXISTING_IMAGE;
import static model.arg.ArgumentType.FILE_OUT;

import model.arg.ArgumentType;

class Save extends AbstractCommand {


  @Override
  public ArgumentType[] getArgumentTypes() {
    return new ArgumentType[]{FILE_OUT, EXISTING_IMAGE};
  }


}
