package controller.viewhandler;

import controller.Controller;
import controller.IControllerView;

public interface ViewHandler {
  void notifyExecutionOnSuccess();
  void notifyExecutionOnFailure(String reason);

  /**
   * Returns false if input is over.
   * @return
   */
  void listenForInput();


  void addController(IControllerView controller);
}
