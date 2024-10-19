package model.command;

/**
 * The {@code Command} interface represents a generic command that can be executed in an image
 * processing system. Implementations of this interface define specific commands, such as loading,
 * saving, or applying transformations to an image.
 *
 * <h3>Example Usage:</h3>
 * <pre>
 *     Command command = new LoadCommand();
 *     command.executeWith();
 *     CommandState status = command.status();
 * </pre>
 * <h3>Developer Notes:</h3>
 * <p>
 *   <ul>
 *     <li>
 *       Each {@code Command} implementation is expected to manage its own argument handling.
 *       Recommended approach is to isolate this logic in the constructor.
 *     </li>
 *   </ul>
 * </p>
 */
interface Command {

  /**
   * Executes the command. This method performs the primary operation of the command.
   *
   * <p>It is expected that implementations will handle the necessary processing
   * and make changes accordingly. Once executed, the status of the operation can be checked using
   * the {@link #status()} method.</p>
   *
   * @throws IllegalStateException if the command is executed in {@code CommandStatus.PENDING} state
   *                               before setting any values.
   */
  void execute() throws IllegalStateException;

  /**
   * Returns the status of the command execution.
   * <p>
   * The status is represented by the {@link CommandStatus} enum.
   * </p>
   *
   * @return a {@link CommandStatus} value representing the current status of the command lifecycle.
   * @see CommandStatus
   */
  CommandStatus status();

}
