package controller.viewhandler;

import controller.Features;

public interface ViewAdapter {

  void notifyExecutionOnSuccess();

  void notifyExecutionOnFailure(String reason);

  /**
   * Returns false if input is over.
   *
   * @return
   */
  void listenForInput();


  void addController(Features controller);
}
