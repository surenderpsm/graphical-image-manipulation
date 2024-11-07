package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import model.Model;

/**
 * This is the controller.
 */
public class Controller {

  private final InputStream in;
  private final PrintStream out;

  public Controller(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  /**
   * Run the controller.
   *
   * @param model new model.
   */
  public void run(Model model) {
    Scanner sc = new Scanner(in);

    while (true) {
      out.println("Enter command: ");
      String command = sc.nextLine();
      String[] tokens = command.split(" ");
      String commandHead = tokens[0].toLowerCase();
      if (commandHead.equals("run")) {
        List<String> scriptCommands = new ScriptHandler(tokens[1]).getCommands();
        for (String cmd : scriptCommands) {
          out.print("EXC: " + cmd);
          commandRunner(model, cmd);
        }
      }
      else if (commandHead.equals("quit")) {
        break;
      }
      else {
        commandRunner(model, command);
      }
    }
  }

  private void commandRunner(Model model, String command) {
    try {
      new CommandExecutor(model, command);
      out.println("\tSTATUS: DONE");
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }

}
