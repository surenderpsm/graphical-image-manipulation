package model.command;

import static model.command.ArgumentTypes.FILE_IN;
import static model.command.ArgumentTypes.NEW_IMAGE;

class Load extends AbstractCommand {


  @Override
  public ArgumentTypes[] getArgumentTypes() {
    return new ArgumentTypes[]{FILE_IN, NEW_IMAGE};
  }

}
