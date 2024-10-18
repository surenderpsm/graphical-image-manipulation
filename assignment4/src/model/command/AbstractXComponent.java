package model.command;

import static model.arg.ArgumentType.EXISTING_IMAGE;
import static model.arg.ArgumentType.NEW_IMAGE;

import model.arg.ArgumentType;


abstract class AbstractXComponent extends AbstractCommand {

  @Override
  public ArgumentType[] getArgumentTypes() {
    return new ArgumentType[]{EXISTING_IMAGE, NEW_IMAGE};
  }
}
