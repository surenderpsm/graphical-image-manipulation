package controller.viewhandler;

public interface ViewHandler {
  void notifyExecutionOnSuccess();
  void notifyExecutionOnFailure(String reason);

  /**
   * Returns false if input is over.
   * @return
   */
  void listenForInput();

}
