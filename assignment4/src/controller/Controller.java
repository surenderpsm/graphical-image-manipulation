package controller;

import java.util.List;
import java.util.Scanner;

public class Controller {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.println("Enter command: ");
      String command = sc.nextLine();
      String[] tokens = command.split(" ");
      command = tokens[0].toLowerCase();
      if (command.equals("run")) {
        try {
          List<String> scriptCommands = new ScriptHandler(tokens[1]).getCommands();
          for (String cmd : scriptCommands) {
            System.out.println("EXECUTING: " + cmd);
            new CommandExecutor(cmd);
            System.out.println("\tSTATUS: DONE");
          }
        } catch (Exception e) {
          System.out.println("ERROR: " + e.getMessage());
        }
      }
      else if (command.equals("quit")) {
        break;
      }
    }
  }

}
