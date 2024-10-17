package model.command;

import static model.command.ArgumentTypes.EXISTING_IMAGE;
import static model.command.ArgumentTypes.NEW_IMAGE;

class RGBCombine extends AbstractCommand {


  @Override
  public ArgumentTypes[] getArgumentTypes() {
    return new ArgumentTypes[]{NEW_IMAGE, EXISTING_IMAGE, EXISTING_IMAGE, EXISTING_IMAGE};
  }

}
