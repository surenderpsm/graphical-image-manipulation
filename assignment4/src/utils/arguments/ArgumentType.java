package utils.arguments;

public enum ArgumentType {
  STRING {
    @Override
    public boolean isValidArgument(Argument arg) {
      return arg instanceof StringArgument;
    }

    @Override
    public Argument prepareArgument(Object object) {
      return new StringArgument(object);
    }
  },
  INT {
    @Override
    public boolean isValidArgument(Argument arg) {
      return arg instanceof IntArgument;
    }

    @Override
    public Argument prepareArgument(Object object) {
      return new IntArgument(object);
    }
  },
  FILE {
    @Override
    public boolean isValidArgument(Argument arg) {
      return arg instanceof FileArgument;
    }

    @Override
    public Argument prepareArgument(Object object) {
      return new FileArgument(object);
    }
  },
  IMAGE {
    @Override
    public boolean isValidArgument(Argument arg) {
      return STRING.isValidArgument(arg);
    }

    @Override
    public Argument prepareArgument(Object object) {
      return new StringArgument(object);
    }

  },
  SPLIT {
    @Override
    public boolean isValidArgument(Argument arg) {
      return (arg instanceof IntArgument) && ((Integer) arg.getArgumentValue() >= 0) && (
          (Integer) arg.getArgumentValue() <= 100);
    }

    @Override
    public Argument prepareArgument(Object object) {
      return new IntArgument(object);
    }
  };

  public abstract boolean isValidArgument(Argument arg);

  public abstract Argument prepareArgument(Object object);
}
