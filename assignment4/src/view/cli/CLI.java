package view.cli;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import view.ViewListener;

/**
 * This class manages the command line interface (CLI). Methods of this class are used by a
 * {@code ViewHandler}, specifically the {@link controller.viewhandler.CLIHandler} to control the
 * CLI.
 */
public class CLI {

  private final Scanner scanner; // Input stream to read commands
  private final PrintStream out; // Output stream to print results
  private final ViewListener controller;
  private final StringBuilder messageBuffer = new StringBuilder();

  /**
   * Instantiates a new CLI.
   *
   * @param in         input stream. may be a FileInputStream or System.in.
   * @param out        usually a printstream.
   * @param controller the viewListener object to pass user inputs and view states.
   */
  public CLI(InputStream in, PrintStream out, ViewListener controller) {
    this.out = out;
    scanner = new Scanner(in);
    this.controller = controller;
  }

  /**
   * Append a string to the messageBuffer. There are situations when we don't want to print the
   * message immediately. The ViewHandler might want to collect a message and then print it all.
   * <br>
   * Use in tandem with {@link CLI#printMessage()} to print the message when the buffer contains the
   * desired message.
   *
   * @param message A {@code String} to append to the buffer.
   */
  public void appendMessage(String message) {
    this.messageBuffer.append('\n').append(message);
  }

  /**
   * Print a message onto the CLI.
   *
   * @param s a {@code String} to print.
   */
  public void printMessage(String s) {
    out.println(s);
    out.println("--------------------------\n");
  }

  /**
   * Dump the message buffer onto the CLI.
   */
  public void printMessage() {
    printMessage(messageBuffer.toString());
    messageBuffer.setLength(0); // reset the messageBuffer.
  }

  /**
   * Used by the ViewHandler to initiate and pass on user input to the viewListener.
   *
   * @return true if input was passed, false if the scanner buffer is empty (no more input).
   */
  public boolean getInput() {

    if (!scanner.hasNextLine()) {
      return false;
    }
    controller.onUserInput(scanner.nextLine());
    return true;
  }
}
