package model.command;

/**
 * The {@code CommandStatus} enum represents the various statuses of a command during its lifecycle.
 * <p>
 * Lifecycle:
 * <ul>
 *   <li><strong>PENDING</strong>: The command is pending and has not yet been initialized.</li>
 *   <li><strong>READY</strong>: The command is ready for execution after initialization.</li>
 *   <li><strong>SUCCESS</strong>: The command has been executed successfully.</li>
 *   <li><strong>FAILURE</strong>: The command has failed to execute.</li>
 * </ul>
 * </p>
 * @see Command
 */
public enum CommandStatus {

  /**
   * The command is pending and has not yet been initialized.
   */
  PENDING,

  /**
   * The command is ready for execution after initialization.
   */
  READY,

  /**
   * The command has been executed successfully.
   */
  SUCCESS,

  /**
   * The command has failed to execute.
   */
  FAILURE
}
