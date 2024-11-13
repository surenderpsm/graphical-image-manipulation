package utils.arguments;

/**
 * Represents an integer argument to be passed to commands in the controller.
 * Allows construction from an Integer or a string that can be parsed to an integer.
 */
public class IntArgument implements Argument {

  private final Integer value;

  /**
   * Constructs an IntArgument from an Integer.
   *
   * @param value the integer value of the argument.
   */
  public IntArgument(Integer value) {
    this.value = value;
  }

  /**
   * Constructs an IntArgument from a string representation of an integer.
   *
   * @param stringValue the string to be parsed as an integer.
   * @throws IllegalArgumentException if the string cannot be parsed as an integer.
   */
  public IntArgument(String stringValue) {
    try {
      this.value = Integer.parseInt(stringValue);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Expected an integer. Unable to parse input to string.");
    }
  }

  /**
   * Retrieves the integer value of this argument.
   *
   * @return the integer value of the argument.
   */
  @Override
  public Integer getArgumentValue() {
    return value;
  }
}
