package view.cli;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import view.ViewListener;

public class CLI {

  private final Scanner scanner; // Input stream to read commands
  private final PrintStream out; // Output stream to print results
  private final ViewListener controller;
  private final StringBuilder message = new StringBuilder();
  public CLI(InputStream in, PrintStream out, ViewListener controller) {
    this.out = out;
    scanner = new Scanner(in);
    this.controller = controller;
  }

  public void appendMessage(String message) {
    this.message.append('\n').append(message);
  }

  public void printMessage(String s) {
    out.println(s);
    out.println("--------------------------\n");
  }

  public void printMessage() {
    printMessage(message.toString());
    message.setLength(0);
  }



  public boolean getInput() {

    if (!scanner.hasNextLine()) {
      return false;
    }
    controller.onUserInput(scanner.nextLine());
    return true;
  }
}
