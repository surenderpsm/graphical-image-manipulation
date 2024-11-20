package utils.arguments;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper for a collection of arguments, allowing them to be accessed by order. Maintains an
 * internal map that associates an argument's position with the {@link Argument} object.
 */
public class ArgumentWrapper implements Cloneable, IntWrapper, FileWrapper, StringWrapper {

  private final Map<Integer, Argument> arguments;
  private final EnumMap<OptionalArgumentKeyword, Argument> optionalArguments;

  public ArgumentWrapper() {
    arguments = new HashMap<>();
    optionalArguments = new EnumMap<>(OptionalArgumentKeyword.class);
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
      arguments.put(i++,
                    arg);
    }
    optionalArguments = new EnumMap<>(OptionalArgumentKeyword.class);
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
    arguments.put(id,
                  arg);
  }

  public void setArgument(String key, Object value) {
      setArgument(OptionalArgumentKeyword.getArgumentType(key),
                  value);
  }

  public void setArgument(OptionalArgumentKeyword key, Object value) {
    optionalArguments.put(key,
                          key.getType().prepareArgument(value));
  }


  /**
   * Add many arguments.
   *
   * @param args
   */
  public void setArguments(Argument... args) {
    int i = length();
    for (Argument arg : args) {
      setArgument(i++,
                  arg);
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

  public int getIntArgument(OptionalArgumentKeyword key) {
    Object obj = getOptionalArgument(key);
    if (obj instanceof Integer) {
      return (Integer) getOptionalArgument(key);
    } else if (obj == Argument.EMPTY){
      throw new IndexOutOfBoundsException("Argument " + key + " is empty");
    }
    throw new IllegalArgumentException("Argument " + key.getArgumentName() + " is not an integer");
  }

  @Override
  public String getStringArgument(int id) {
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

  public Argument getArgumentAt(int id){
    return arguments.get(id);
  }

  private Object getOptionalArgument(OptionalArgumentKeyword key) {
    try{
      return optionalArguments.get(key).getArgumentValue();
    } catch (NullPointerException e) {
      return Argument.EMPTY;
    }
  }

  @Override
  public ArgumentWrapper clone() {
    ArgumentWrapper clone = new ArgumentWrapper();
    // TODO: copy mutable state here, so the clone can't change the internals of the original
    for(int i = 0; i < length(); i++){
      clone.setArgument(i, arguments.get(i));
    }
    return clone;
  }
}
