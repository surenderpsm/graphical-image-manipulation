package model.command;

import model.arg.ArgumentWrapper;

abstract class AbstractCommand implements Command {

  public AbstractCommand() {

  }


  @Override
  public void setArguments(ArgumentWrapper arguments) {

  }

  @Override
  public void execute() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public CommandStatus status() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
