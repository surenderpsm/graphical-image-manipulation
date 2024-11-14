package utils.arguments;

public enum ArgumentType {
  STRING {
    @Override
    public boolean isValidArgument(Argument arg) {
      return arg instanceof StringArgument;
    }
  },
  INT {
    @Override
    public boolean isValidArgument(Argument arg) {
      return arg instanceof IntArgument;
    }
  },
  FILE {
    @Override
    public boolean isValidArgument(Argument arg) {
      return arg instanceof FileArgument;
    }
  };

  public abstract boolean isValidArgument(Argument arg);
}
