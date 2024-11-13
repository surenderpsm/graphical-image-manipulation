package utils.arguments;

/**
 * Represents an argument that can be passed to commands in the controller.
 * Classes implementing this interface provide a specific type of argument value.
 */
public interface Argument {

  /**
   * Retrieves the value of this argument.
   *
   * @return the argument value as an Object.
   */
  Object getArgumentValue();
}
