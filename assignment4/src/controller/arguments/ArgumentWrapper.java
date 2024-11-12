package controller.arguments;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used as a wrapper class to pass arguments from a view to a controller. This class
 * uses an internal HashMap to map the argument (ordering) number to the {@link Argument}.
 * <h3>Usage:</h3>
 * <pre>
 *   ArgumentWrapper args = new ArgumentWrapper(new FileArgument("res/koala.png"),
 *                                              new IntArgument(1),
 *                                              new StringArgument("hello"));
 * </pre>
 */
public class ArgumentWrapper {

  private final Map<Integer, Argument> arguments;

  /**
   * This constructor can be used create an empty {@code ArgumentWrapper}.
   */
  public ArgumentWrapper() {
    arguments = new HashMap<>();
  }

  /**
   * This constructor takes in a variable length of {@code Argument} to set in the
   * {@code ArgumentWrapper}.
   *
   * @param args variable length list of args of type {@link Argument}
   */
  public ArgumentWrapper(Argument... args) {
    arguments = new HashMap<>();
    int i = 1;
    for (Argument arg : args) {
      arguments.put(i++, arg);
    }
  }

  /**
   * Get the argument.
   *
   * @param id of type {@code int}
   * @return an object of type {@link Argument}.
   */
  public Argument getArgument(int id) {
    return arguments.get(id);
  }

  /**
   * Set an argument in the internal map.
   *
   * @param id  of type {@code int}
   * @param arg of type {@link Argument}
   */
  public void setArgument(int id, Argument arg) {
    arguments.put(id, arg);
  }
}
