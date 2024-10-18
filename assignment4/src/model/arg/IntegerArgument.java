package model.arg;

public class IntegerArgument implements Argument {

  private final Integer value;

  public IntegerArgument(Integer value) {
    this.value = value;
  }

  @Override
  public Integer getValue() {
    return value;
  }
}
