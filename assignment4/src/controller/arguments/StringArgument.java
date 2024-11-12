package controller.arguments;

public class StringArgument implements Argument {
  private final String value;
  public StringArgument(String value) {
    if (containsWhitespace(value)) {
      throw new IllegalArgumentException("Names cannot have whitespaces in them.");
    }
    this.value = value;
  }
  @Override
  public String getArgumentValue() {
    return value;
  }

  private static boolean containsWhitespace(String value) {
    for (int i = 0; i < value.length(); i++) {
      if (!Character.isWhitespace(value.charAt(i))) {
        return true;
      }
    }
    return false;
  }
}
