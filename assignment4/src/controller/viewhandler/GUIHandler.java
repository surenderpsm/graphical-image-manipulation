package controller.viewhandler;

import view.gui.GUI;

public class GUIHandler implements ViewHandler {
  GUI gui;

  @Override
  public void notifyExecutionOnSuccess() {
    // update display.

  }

  @Override
  public void notifyExecutionOnFailure(String reason) {
    // display an alert for failure.
  }
}
