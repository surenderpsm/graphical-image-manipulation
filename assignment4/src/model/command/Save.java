package model.command;

import static model.command.ArgumentTypes.EXISTING_IMAGE;
import static model.command.ArgumentTypes.FILE_OUT;

class Save extends AbstractCommand {


  @Override
  public ArgumentTypes[] getArgumentTypes() {
    return new ArgumentTypes[]{FILE_OUT, EXISTING_IMAGE};
  }


}
