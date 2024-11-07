package controller;

import java.io.IOException;
import model.Model;


/**
 * The {@code CommandExecutor} class is responsible for parsing and executing a command based on
 * user input.
 *
 * <p>This class processes the input command string, identifies the appropriate action,
 * and delegates the execution to the corresponding method based on the command type.</p>
 *
 * <p>If an error occurs during the parsing or execution of the command, an
 * {@code IllegalArgumentException} or {@code UnsupportedOperationException} may be thrown.</p>
 *
 * <h2>Example Usage:</h2>
 * <pre>
 *     new CommandExecutor(model, "load somefile.png koala");
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
    // check if a IO command.
    if (command.equals("load") || command.equals("save")) {
      new IOHandler(model, command, args);
      return;
    }
    // otherwise pass it to the model.
    model.execute(command, args);
  }
}
