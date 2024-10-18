package model.command;

import model.arg.ArgumentWrapper;
import model.arg.ArgumentType;

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
public interface Command {

  /**
   * Returns the argument types required for the command.
   *
   * @return an array of {@link ArgumentType} indicating the types of arguments needed for this
   *     command.
   */
  ArgumentType[] getArgumentTypes();

  /**
   * Sets the arguments for the command.
   *
   * @param arguments an {@link ArgumentWrapper} that contains the arguments needed to execute the
   *                  command. The size and types of the arguments should match the requirements
   *                  defined by {@link #getArgumentTypes()}.
   * @throws IllegalArgumentException if the provided arguments do not match the expected types.
   * @see ArgumentWrapper
   */
  void setArguments(ArgumentWrapper arguments);

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
   * <br>
   * The status is represented by the {@link CommandStatus} enum.
   *
   * @return a {@link CommandStatus} value representing the current status of the command execution.
   * @see CommandStatus
   */
  CommandStatus status();

}
