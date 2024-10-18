package model.command;

import static model.arg.ArgumentType.EXISTING_IMAGE;
import static model.arg.ArgumentType.NEW_IMAGE;

import model.arg.ArgumentType;

class RGBCombine extends AbstractCommand {


  @Override
  public ArgumentType[] getArgumentTypes() {
    return new ArgumentType[]{NEW_IMAGE, EXISTING_IMAGE, EXISTING_IMAGE, EXISTING_IMAGE};
  }

}
