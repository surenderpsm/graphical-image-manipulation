package controller.viewhandler;

public interface ViewHandler {
  void notifyExecutionOnSuccess();
  void notifyExecutionOnFailure(String reason);
}
