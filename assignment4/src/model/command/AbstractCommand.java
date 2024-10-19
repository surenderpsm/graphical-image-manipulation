package model.command;


import java.lang.reflect.InvocationTargetException;

abstract class AbstractCommand implements Command {

  private CommandStatus status = CommandStatus.READY;
  private final String[] args;

  AbstractCommand(String rawArguments) {
    args = rawArguments.split(" ");
  }

  protected String getArg(int argumentNumber) throws IndexOutOfBoundsException {
    return args[argumentNumber+1];
  }
  protected int numberOfArgs() {
    return args.length;
  }
  protected void setStatus(CommandStatus status) {
    this.status = status;
  }

  @Override
  public void execute(){
    status = CommandStatus.FAILURE;
    try{
      status = run() ? CommandStatus.SUCCESS : CommandStatus.FAILURE;
    }
    catch (Exception e){
      // @todo rework this.
      System.out.println(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }
  /**
   * This method is required by all concrete classes. This method defines core logic of what the
   * command does.
   * @return true if the command execution is successful, otherwise false if some failure occurs.
   */
  protected abstract boolean run();

  @Override
  public CommandStatus status() {
    return status;
  }
}
