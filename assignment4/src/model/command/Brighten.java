package model.command;

import static model.command.ArgumentTypes.EXISTING_IMAGE;
import static model.command.ArgumentTypes.INTEGER;
import static model.command.ArgumentTypes.NEW_IMAGE;

class Brighten extends AbstractCommand {

  @Override
  public ArgumentTypes[] getArgumentTypes() {
    return new ArgumentTypes[]{INTEGER, EXISTING_IMAGE, NEW_IMAGE};
  }
}
