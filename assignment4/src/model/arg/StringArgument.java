package model.arg;

public class StringArgument implements Argument {

  private final String value;

  public StringArgument(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }

}
