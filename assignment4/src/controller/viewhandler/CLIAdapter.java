package controller.viewhandler;

import controller.Features;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.arguments.ArgumentWrapper;
import utils.arguments.MandatedArgWrapper;
import utils.arguments.StringArgument;
import view.cli.CLI;
import view.cli.CLIObserver;

public class CLIAdapter implements ViewAdapter, CLIObserver {

  CLI cli;
  Features controller;

  public CLIAdapter(InputStream in, PrintStream out) {
    cli = new CLI(in, out, this);
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
  public void addController(Features controller) {
    this.controller = controller;
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
          controller.loadImage(new File(tokens[1]), tokens[2]);
          break;
        case "save":
          controller.saveImage(new File(tokens[1]), tokens[2]);
          break;

        case "blur":
        case "sharpen":
        case "grayscale":
        case "sepia":
        case "red-component":
        case "green-component":
        case "blue-component":

          if (tokens.length == 4) {
            // we need to store mask image
            controller.invokeCommand(commandHead,
                                     new ArgumentWrapper(new StringArgument(tokens[1]),
                                                         new StringArgument(tokens[3])));
            ArgumentWrapper
                wrapper =
                new ArgumentWrapper(new StringArgument(tokens[1]),
                                    new StringArgument(tokens[3]),
                                    new StringArgument(tokens[3]));
            wrapper.setArgument("maskimg", tokens[2]);
            controller.invokeCommand("partial-process", wrapper);

          }
          else {
            controller.invokeCommand(commandHead,
                                     new ArgumentWrapper(new StringArgument(tokens[1]),
                                                         new StringArgument(tokens[2])));
          }
          break;
        default:
          MandatedArgWrapper wrapper1 = controller.getMandatedArgs(commandHead);

          for (int i = 1; i < tokens.length; i++) {
            if (i > wrapper1.expectedLength()) {
              // Mandatory arguments are collected. Now check for optional args.
              wrapper1.setArgument(tokens[i], tokens[i + 1]);
              i += 1;
            }
            else {
              wrapper1.setArgument(i - 1, tokens[i]);
            }

          }
          controller.invokeCommand(commandHead, wrapper1);

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
