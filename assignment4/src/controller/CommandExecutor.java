package controller;

import java.io.IOException;
import model.Model;


/**
 * The {@code CommandExecutor} class is responsible for executing a command given a user input.
 *
 * <p>This class processes the input command string, determines which command to executeWith
 * from the {@code CommandEnum} enum, and invokes the corresponding command's execution method.
 * </p>
 *
 * <p>If an error occurs during command
 * instantiation or execution, an {@code UnsupportedOperationException} is thrown.
 * </p>
 *
 * <h2>Example Usage:</h2>
 * <pre>
 *     CommandExecutor executor = new CommandExecutor("load somefile.png koala");
 *     executor.executeWith();
 *     boolean success = executor.isSuccess();
 * </pre>
 */
class CommandExecutor {

  /**
   * Constructs a {@code CommandExecutor} with a command string.
   *
   * <p>The command string should consist of the command name followed by the
   * arguments required for the command. The string is split into the command and arguments, which
   * are handled by the {@code ArgumentHandler}.</p>
   *
   * @param model         An instance of the model.
   * @param commandString the input string containing the command and its arguments.
   */
  public CommandExecutor(Model model, String commandString) throws IOException {
    String[] tokens = commandString.split(" ", 2);
    String command = null;
    String args = null;
    try {
      command = tokens[0];
      args = tokens[1];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Insufficient arguments.");
    }
    if (command.equals("load") || command.equals("save")) {
      new IOHandler(model, command, args);
      return;
    }
    model.execute(command, args);
  }
}
