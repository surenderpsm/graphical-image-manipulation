package controller.viewhandler;

import controller.IControllerView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.arguments.MandatedArgWrapper;
import view.ViewListener;
import view.cli.CLI;

public class CLIHandler implements ViewHandler, ViewListener {

  CLI cli;
  IControllerView controller;

  public CLIHandler(InputStream in, PrintStream out, IControllerView controller) {
    cli =
        new CLI(in,
                out,
                this);
    this.controller = controller;
  }

  @Override
  public void notifyExecutionOnFailure(String reason) {
    cli.appendMessage(reason);
  }

  @Override
  public void listenForInput() {
    if (!cli.getInput()) {
      controller.exitApplication();
    }
  }

  @Override
  public boolean requestApplicationExit() {
    return false;
  }

  @Override
  public void notifyExecutionOnSuccess() {
    cli.appendMessage("\nSTATUS: DONE");
  }

  @Override
  public void onUserInput(String input) {
    String[] tokens = input.split(" ");
    String commandHead = tokens[0].toLowerCase();
    switch (commandHead) {
      case "run":
        if (tokens.length > 1) {
          List<String> scriptCommands = parseScript(tokens[1]);
          for (String cmd : scriptCommands) {
            commandRunner(cmd);
          }
        }
        else {
          cli.printMessage("Incorrect use of run command");
        }
        break;
      case "quit":
        if (tokens.length == 1) {
          controller.exitApplication();
        }
        else {
          cli.printMessage("Incorrect use of quit command");
        }
        break;
      default:
        if (!input.startsWith("#") && !input.isEmpty()) {
          commandRunner(input);
        }
    }
  }

  private List<String> parseScript(String path) {
    List<String> commands = new ArrayList<>();
    try (Scanner sc = new Scanner(new File(path))) {
      while (sc.hasNextLine()) {
        String line = sc.nextLine().trim();
        if (!line.startsWith("#") && !line.isEmpty()) {
          commands.add(line);
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: The specified path is invalid.");
    }
    return commands;
  }


  private void commandRunner(String command) {
    cli.appendMessage(command);
    String[] tokens = command.split(" ");
    String commandHead = tokens[0].trim().toLowerCase();
    try {
      switch (commandHead) {
        case "load":
          controller.loadImage(new File(tokens[1]),
                               tokens[2]);
          break;
        case "save":
          controller.saveImage(new File(tokens[1]),
                               tokens[2]);
          break;
        default:
          MandatedArgWrapper wrapper = controller.getMandatedArgs(commandHead);

          for (int i = 1; i < tokens.length; i++) {
            if (i > wrapper.expectedLength()) {
              // Mandatory arguments are collected. Now check for optional args.
              wrapper.setArgument(tokens[i],
                                  tokens[i + 1]);
              i += 1;
            }
            else {
              wrapper.setArgument(i - 1,
                                  tokens[i]);
            }

          }
          controller.invokeCommand(commandHead,
                                   wrapper);

      }
    } catch (ArrayIndexOutOfBoundsException e) {
      cli.appendMessage("ERROR: Incorrect number of arguments for command: " + commandHead);
    } catch (Exception e) {
      cli.appendMessage("ERROR: " + e.getMessage());
    } finally {
      cli.printMessage();
    }
  }

}
