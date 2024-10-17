package model.command;

/**
 * The {@code Command} interface represents a generic command that can be executed in an image
 * processing system. Implementations of this interface define specific commands, such as loading,
 * saving, or applying transformations to an image.
 *
 * <h2>Example Usage:</h2>
 * <pre>
 *     Command command = new LoadCommand();
 *     command.execute();
 *     boolean success = command.status();
 * </pre>
 */
interface Command {

  /**
   * Executes the command. This method performs the primary operation of the command.
   *
   * <p>It is expected that implementations will handle the necessary processing
   * and make changes accordingly. Once executed, the status of the operation can be checked using
   * the {@link #status()} method.</p>
   */
  void execute();

  /**
   * Returns the status of the command execution.
   *
   * @return {@code true} if the command executed successfully, or {@code false} if the operation
   *     was unsuccessful or hasn't been executed yet.
   */
  boolean status();
}
