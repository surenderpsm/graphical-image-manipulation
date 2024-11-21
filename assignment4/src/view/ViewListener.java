package view;

/**
 * This is a view observer that can be implemented by the ViewHandler,
 */
public interface ViewListener {

  /**
   * The calling object of this method needs confirmation from the controller if the application is
   * ready to exit. If there are changes that have not been saved, the application is not ready to
   * exit and needs to confirm with the user whether they want to exit without saving.
   *
   * @return {@code true} if all changes have been saved, otherwise {@code false}.
   */
  boolean requestApplicationExit();
}
