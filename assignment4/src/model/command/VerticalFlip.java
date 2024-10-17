package model.command;

import static model.command.ArgumentTypes.EXISTING_IMAGE;
import static model.command.ArgumentTypes.NEW_IMAGE;

class VerticalFlip extends AbstractCommand {

  @Override
  public ArgumentTypes[] getArgumentTypes() {
    return new ArgumentTypes[]{EXISTING_IMAGE, NEW_IMAGE};
  }

}
