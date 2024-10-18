package model.command;

import static model.arg.ArgumentType.EXISTING_IMAGE;
import static model.arg.ArgumentType.INTEGER;
import static model.arg.ArgumentType.NEW_IMAGE;

import model.arg.ArgumentType;

class Brighten extends AbstractCommand {

  @Override
  public ArgumentType[] getArgumentTypes() {
    return new ArgumentType[]{INTEGER, EXISTING_IMAGE, NEW_IMAGE};
  }
}
