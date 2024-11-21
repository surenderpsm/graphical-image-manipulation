package view;

/**
 * The {@code ViewObserver} interface defines a contract for observing and responding
 * to events or state changes in the view layer. It can be implemented by classes such
 * as a {@code ViewAdapter} to handle specific view-related interactions.
 */
public interface ViewObserver {

  /**
   * Requests confirmation from the controller to determine if the application is ready to exit.
   * If there are unsaved changes, the application must confirm with the user whether they want
   * to exit without saving.
   *
   * @return {@code true} if all changes have been saved and the application is ready to exit,
   *         otherwise {@code false}.
   */
  boolean requestApplicationExit();
}
