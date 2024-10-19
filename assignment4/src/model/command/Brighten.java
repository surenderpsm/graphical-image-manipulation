package model.command;


class Brighten extends AbstractCommand {

  Brighten(String rawArguments) {
    super(rawArguments);
  }

  @Override
  protected boolean run() {
    return false;
  }

}
