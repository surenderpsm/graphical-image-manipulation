package controller;

import java.lang.reflect.InvocationTargetException;
import model.arg.ArgumentWrapper;
import model.command.Command;
import model.command.CommandStatus;
import model.command.Commands;

/**
 * The {@code CommandExecutor} class is responsible for executing commands based on user input.
 *
 * <p>This class processes the input command string, determines which command to execute
 * from the {@code Commands} enum, and invokes the corresponding command's execution method.
 * </p>
 *
 * <p>If an error occurs during command
 * instantiation or execution, an {@code UnsupportedOperationException} is thrown.
 * </p>
 *
 * <h2>Example Usage:</h2>
 * <pre>
 *     CommandExecutor executor = new CommandExecutor("load somefile.png koala");
 *     executor.execute();
 *     boolean success = executor.isSuccess();
 * </pre>
 *
 * @see model.command.Commands
 * @see controller.ArgumentHandler
 * @see ArgumentWrapper
 */
public class CommandExecutor {

  private final String command;
  private final ArgumentHandler args;
  private boolean isSuccess;

  /**
   * Constructs a {@code CommandExecutor} with a command string.
   *
   * <p>The command string should consist of the command name followed by the
   * arguments required for the command. The string is split into the command and arguments, which
   * are handled by the {@code ArgumentHandler}.</p>
   *
   * @param commandString the input string containing the command and its arguments.
   */
  public CommandExecutor(String commandString) {
    String[] tokens = commandString.split(" ", 1);
    this.command = tokens[0];
    this.args = new ArgumentHandler(tokens[1]);
    this.isSuccess = false;
  }

  /**
   * Executes the command specified during the creation of the {@code CommandExecutor}.
   *
   * <p>This method searches for a matching command in the {@code Commands} enum,
   * and invokes its {@code executeWith} method. If an error occurs during command instantiation or
   * execution, an {@code UnsupportedOperationException} is thrown with a detailed message.</p>
   *
   * @throws UnsupportedOperationException if the command is not found or if there is an error
   *                                       during command instantiation or execution
   */
  public void execute() throws UnsupportedOperationException {
    for (Commands c : Commands.values()) {
      if (c.getCommandName().equals(command)) {
        try {
          Command cmd = c.getCommand();
          args.prepareArguments(cmd.getArgumentTypes());
          cmd.setArguments(args.getAllArguments());
          cmd.execute();
          CommandStatus status = cmd.status();
        } catch (NoSuchMethodException e) {
          throw new UnsupportedOperationException(
              "Internal Error: The specified command " + c.getCommandName() + " has no "
                  + "constructors.");
        } catch (InvocationTargetException e) {
          throw new UnsupportedOperationException(
              "Internal Error: There was an error in constructor invocation of command "
                  + c.getCommandName() + " : " + e.getCause().getMessage());
        } catch (InstantiationException e) {
          throw new UnsupportedOperationException(
              "Internal Error: The specified command " + c.getCommandName()
                  + " cannot be instantiated.");
        } catch (IllegalAccessException e) {
          throw new UnsupportedOperationException(
              "Internal Error: The specified command" + c.getCommandName()
                  + " cannot be accessed.");
        }
      }
    }
    throw new UnsupportedOperationException("User Error: Input command not found.");
  }

  /**
   * Returns the success status of the command execution.
   *
   * <p>The status is {@code true} if the command executed successfully, and {@code false}
   * if the command failed or has not been executed yet.</p>
   *
   * @return {@code true} if the command was executed successfully, {@code false} otherwise
   */
  public boolean isSuccess() {
    return isSuccess;
  }

}
