package utils.arguments;

/**
 * Optional Argument keywords that are valid in a given command. Currently, supports only one entity
 * in the argument. Each entry is associated with an appropriate ArgumentType. For example, split is
 * of type {@code ArgumentType.INT}. A command string is also associated with it.
 * <br>
 * This enum supports a static method to check if a given command string is an optional argument
 * keyword.
 * <br>
 * The syntax of an optional argument keyword is assumed that a keyword will be followed by one
 * argument.
 */
public enum OptionalArgumentKeyword {
  SPLIT(ArgumentType.INT, "split"),
  MASKIMG(ArgumentType.IMAGE, "maskimg");

  private ArgumentType argumentType;
  private String argumentName;

  private OptionalArgumentKeyword(ArgumentType argumentType, String argumentName) {
    this.argumentType = argumentType;
    this.argumentName = argumentName;
  }

  public ArgumentType getType() {
    return argumentType;
  }

  public String getArgumentName() {
    return argumentName;
  }

  public static OptionalArgumentKeyword getArgumentType(String argumentName) {
    for (OptionalArgumentKeyword type : OptionalArgumentKeyword.values()) {
      if (type.argumentName.equals(argumentName)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid token: " + argumentName);
  }

  public static boolean isOptionalArgument(String s) {
    for (OptionalArgumentKeyword type : OptionalArgumentKeyword.values()) {
      if (type.argumentName.equals(s)) {
        return true;
      }
    }
    return false;
  }
}
