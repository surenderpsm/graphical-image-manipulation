package model.arg;

import java.util.ArrayList;
import java.util.List;

public class ArgumentWrapper {

  private final List<Argument> arguments = new ArrayList<Argument>();

  public void addArgument(Argument arg) {
    arguments.add(arg);
  }

  /**
   * Get the argument at a particular index.
   * <h2>Usage:</h2>
   * <pre>
   *   ArgumentWrapper args;
   *   ...
   *   args.argumentAt(1);     // This gets the first argument.
   *   args.argumentAt(2);     // This gets the second argument.
   * </pre>
   *
   * @param index A 1-indexed integer input to get the argument.
   * @return an Argument object.
   * @throws IllegalArgumentException if the argument is not found.
   */
  public Argument argumentAt(int index) throws IllegalArgumentException {
    index++;
    if (index < 1 || index > arguments.size()) {
      throw new IllegalArgumentException("Argument not found.");
    }
    return arguments.get(index);
  }

}
