package controller.viewhandler;

import view.cli.CLI;

public class CLIHandler implements ViewHandler {

  CLI cli;

  @Override
  public void notifyExecutionOnFailure(String reason) {
    cli.printMessage(reason);
  }

  @Override
  public void notifyExecutionOnSuccess() {
    cli.printMessage("\nSTATUS: DONE");
  }


}
