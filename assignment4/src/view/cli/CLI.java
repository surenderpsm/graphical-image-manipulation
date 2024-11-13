package view.cli;

import controller.CommandExecutor;
import java.io.InputStream;
import java.io.PrintStream;
import view.View;

public class CLI  {

  private final InputStream in; // Input stream to read commands
  private final PrintStream out; // Output stream to print results

  public CLI(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  public void printMessage(String s) {
    out.println(s);
    out.println("--------------------------\n");
  }

}
