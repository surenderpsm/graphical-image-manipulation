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
            new CommandExecutor(cmd);
          }
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
      else if (command.equals("quit")) {
        break;
      }
    }
  }

}
