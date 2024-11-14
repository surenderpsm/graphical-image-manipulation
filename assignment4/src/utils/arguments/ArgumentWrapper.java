package utils.arguments;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
/**
 * A wrapper for a collection of arguments, allowing them to be accessed by order. Maintains an
 * internal map that associates an argument's position with the {@link Argument} object.
 */
public class ArgumentWrapper implements IntWrapper, FileWrapper, StringWrapper {

  private final Map<Integer, Argument> arguments;

  public ArgumentWrapper() {
    arguments = new HashMap<>();
  }

  /**
   * Creates an ArgumentWrapper and populates it with a series of arguments in order.
   *
   * @param args a variable-length list of Argument objects.
   */
  public ArgumentWrapper(Argument... args) {
    arguments = new HashMap<>();
    int i = 0;
    for (Argument arg : args) {
      arguments.put(i++, arg);
    }
  }

  public int length() {
    return arguments.size();
  }

  /**
   * Adds or updates an argument at a specific position in the wrapper.
   *
   * @param id  the positional index for the argument.
   * @param arg the Argument to add or update in the wrapper.
   */
  public void setArgument(int id, Argument arg) {
    arguments.put(id, arg);
  }

  /**
   * Add many arguments. Ensures that it matches a mandate if exists.
   * @param args
   */
  public void setArguments(Argument... args) {
    int i = 0;
    for(Argument arg : args) {
      setArgument(i++, arg);
    }
  }

  @Override
  public File getFileArgument(int id) {
    if (getArgument(id) instanceof File) {
      return (File) getArgument(id);
    }
    throw new IllegalArgumentException("Argument " + id + " is not a file");
  }

  @Override
  public int getIntArgument(int id) {
    if (getArgument(id) instanceof Integer) {
      return (Integer) getArgument(id);
    }
    throw new IllegalArgumentException("Argument " + id + " is not an integer");
  }

  @Override
  public String getString(int id) {
    if (getArgument(id) instanceof String) {
      return (String) getArgument(id);
    }
    throw new IllegalArgumentException("Argument " + id + " is not a string");
  }

  /**
   * Retrieves an argument based on its position in the argument list.
   *
   * @param id the positional index of the argument.
   * @return the Argument object at the specified position, or null if not present.
   */
  private Object getArgument(int id) {
    try {
      return arguments.get(id).getArgumentValue();
    } catch (NullPointerException e) {
      throw new IndexOutOfBoundsException("Argument does not exist.");
    }
  }
}
