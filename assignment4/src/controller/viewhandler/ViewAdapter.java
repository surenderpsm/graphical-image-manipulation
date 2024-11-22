package controller.viewhandler;

import controller.Features;

/**
 * The ViewAdapter interface provides an abstraction for the communication
 * between the controller and different types of views (GUI and CLI).
 * <p>
 * This interface defines methods for notifying the execution result,
 * listening for user input, and adding the controller to the view. The
 * implementing classes—such as the GUI and CLI adapters—ensure that the
 * controller can interact with both types of views. Each adapter connects
 * the appropriate view (GUI or CLI) to the controller, enabling seamless
 * communication between the two.
 * </p>
 */

public interface ViewAdapter {

  /**
   * Notifies the view that an operation has been successfully executed.
   * This method should be called after an operation completes successfully.
   */
  void notifyExecutionOnSuccess();

  /**
   * Notifies the view that an operation has failed.
   *
   * @param reason a message explaining the reason for the failure.
   */
  void notifyExecutionOnFailure(String reason);

  /**
   * Listens for user input. This method should be called to start listening
   * for input events. It returns false if there are no more inputs to process.
   */
  void listenForInput();

  /**
   * Adds the given controller to the view adapter.
   *
   * @param controller the controller that will handle the features for this view.
   */
  void addController(Features controller);
}
