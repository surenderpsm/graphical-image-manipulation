package utils.arguments;

/**
 * Represents a string argument to be passed to commands in the controller. Throws an exception if
 * the provided string contains whitespace.
 */
public class StringArgument implements Argument {

  private final String value;

  /**
   * Constructs a StringArgument, ensuring no whitespace is present.
   *
   * @param value the string value for the argument.
   * @throws IllegalArgumentException if the value contains whitespace.
   */
  public StringArgument(String value) {
    if (containsWhitespace(value)) {
      throw new IllegalArgumentException("Names cannot have whitespaces in them.");
    }
    this.value = value;
  }

  public StringArgument(Object object) {
    if (object instanceof String) {
      this.value = (String) object;
    }
    else {
      throw new IllegalArgumentException("Expected a String.");
    }
  }

  /**
   * Retrieves the string value of this argument.
   *
   * @return the string value of the argument.
   */
  @Override
  public String getArgumentValue() {
    return value;
  }

  // Helper method to check for whitespace in the input string.
  private static boolean containsWhitespace(String value) {
    for (int i = 0; i < value.length(); i++) {
      if (Character.isWhitespace(value.charAt(i))) {
        return true;
      }
    }
    return false;
  }
}
