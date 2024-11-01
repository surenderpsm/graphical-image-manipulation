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
   * @param model new model.
   */
  public void run(Model model) {
    Scanner sc = new Scanner(in);

    while (true) {
      out.println("Enter command: ");
      String command = sc.nextLine();
      String[] tokens = command.split(" ");
      command = tokens[0].toLowerCase();
      if (command.equals("run")) {
        try {
          List<String> scriptCommands = new ScriptHandler(tokens[1]).getCommands();
          for (String cmd : scriptCommands) {
            out.print("EXC: " + cmd);
            new CommandExecutor(model, cmd);
            out.println("\tSTATUS: DONE");
          }
        } catch (Exception e) {
          out.println("\n\n: " + e.getMessage());
        }
      }
      else if (command.equals("quit")) {
        break;
      }
      else {
        out.println("ERROR: Unknown command: " + command);
      }
    }
  }

}
