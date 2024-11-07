package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import model.Model;

/**
 * The Controller class is responsible for managing the flow of input and output,
 * handling commands from the user, and interacting with the model.
 * It processes input commands, including script commands and regular commands,
 * and calls the appropriate methods on the model.
 */
public class Controller {

  private final InputStream in; // Input stream to read commands
  private final PrintStream out; // Output stream to print results

  /**
   * Constructor for the Controller class.
   *
   * @param in  the input stream from which commands will be read
   * @param out the output stream to which results will be printed
   */
  public Controller(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Main method to run the controller. It processes commands in a loop until the user quits.
   * It handles both individual commands and script files.
   *
   * @param model the model to interact with while processing commands
   */
  public void run(Model model) {
    Scanner sc = new Scanner(in);

    while (true) {
      if (!sc.hasNextLine()) {
        break;
      }
      String command = sc.nextLine(); // read the user's input.
      String[] tokens = command.split(" ");
      String commandHead = tokens[0].toLowerCase();
      // Check if the command is to run a script.
      if (commandHead.equals("run")) {
        List<String> scriptCommands = new ScriptHandler(tokens[1]).getCommands();
        for (String cmd : scriptCommands) {
          commandRunner(model, cmd);
        }
      }
      // Check if the command is to quit the program.
      else if (commandHead.equals("quit")) {
        break;
      }
      // Pass it to the model.
      else {
        commandRunner(model, command);
      }
    }
  }
  /**
   * Executes the given command on the model and handles any exceptions.
   *
   * @param model   the model to interact with
   * @param command the command to execute
   */
  private void commandRunner(Model model, String command) {
    try {
      // Only execute commands that aren't comments or empty
      if (!command.startsWith("#") && !command.isEmpty()) {
        out.println("EXC: " + command); // Print the command being executed
        new CommandExecutor(model, command); // Execute the command on the model
        out.println("\nSTATUS: DONE"); // Print status if the command was successfully executed
        out.println("--------------------------\n");
      }
    } catch (Exception e) {
      out.println("\n" + e.getMessage()); // Print any exceptions that occur during execution
      out.println("--------------------------\n");
    }
  }

}
