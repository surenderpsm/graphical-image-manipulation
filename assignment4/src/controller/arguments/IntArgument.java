package controller.arguments;

public class IntArgument implements Argument {
  Integer value;
  public IntArgument(Integer value) {
    this.value = value;
  }
  public IntArgument(String stringValue) {
    try {
      this.value = Integer.parseInt(stringValue);
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException("Expected an integer. Unable to parse input to string.");
    }
  }
  @Override
  public Integer getArgumentValue() {
    return value;
  }
}
