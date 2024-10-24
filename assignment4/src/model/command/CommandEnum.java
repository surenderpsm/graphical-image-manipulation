package model.command;


import java.lang.reflect.InvocationTargetException;


/**
 * The {@code CommandEnum} enum serves as the sole public entry point for the command package,
 * representing a collection of commands for various operations in the image processing application.
 * This class implements the factory pattern to facilitate the dynamic instantiation of command
 * classes that implement the {@code Command} interface.
 *
 * <p>
 * Each enum constant is associated with a specific command class and a string representation of the
 * command name. This structure allows for easy retrieval and execution of commands based on user
 * input.
 * </p>
 *
 * <p>
 * The enum provides the {@link CommandEnum#executeCommandWith(String)} method to run commands with
 * the provided arguments, abstracting the details of command instantiation.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 *     // Executes the brighten command with the specified arguments
 *     CommandEnum.BRIGHTEN.executeCommandWith("koala new-koala-brighten");
 * </pre>
 *
 * @see Command
 */
public enum CommandEnum {
  NONE(null, ""),
  BRIGHTEN(Brighten.class, "brighten"),
  VERTICAL_FLIP(VerticalFlip.class, "vertical-flip"),
  HORIZONTAL_FLIP(HorizontalFlip.class, "horizontal-flip"),
  RGB_SPLIT(RGBSplit.class, "rgb-split"),
  RGB_COMBINE(RGBCombine.class, "rgb-combine"),
  BLUR(Blur.class, "blur"),
  SHARPEN(Sharpen.class, "sharpen"),
  SEPIA(Sepia.class, "sepia"),
  RED_COMPONENT(RedComponent.class, "red-component"),
  GREEN_COMPONENT(GreenComponent.class, "green-component"),
  BLUE_COMPONENT(BlueComponent.class, "blue-component"),
  VALUE_COMPONENT(ValueComponent.class, "value-component"),
  LUMA_COMPONENT(LumaComponent.class, "luma-component"),
  INTENSITY_COMPONENT(IntensityComponent.class, "intensity-component"),
  ;


  private final Class<? extends Command> commandClass;
  private final String commandName;

  /**
   * Constructs a {@code CommandEnum} enum constant with the specified command class and name.
   *
   * @param command     the class representing the command associated with this enum constant
   * @param commandName the string name of the command
   */
  CommandEnum(Class<? extends Command> command, String commandName) {
    this.commandClass = command;
    this.commandName = commandName;
  }

  /**
   * Executes a command with the specified arguments.
   *
   * <p>This method instantiates a {@code Command} object based on the provided
   * arguments and executes it. This method is designed to be used within the model, ensuring that
   * no other classes outside the command package can directly access a {@code Command} object.</p>
   *
   * @param args the arguments to be passed to the command constructor.
   */
  public void executeCommandWith(String args) {
    if (this == CommandEnum.NONE) {
      throw new IllegalStateException("Illegal state: No command has been assigned to Model.");
    }
    Command c = instantiateCommand(args);
    c.execute();
  }

  /**
   * Returns the name of the command associated with this enum constant.
   *
   * <p>
   * This name is used for identifying the command in user input or configuration files.
   * </p>
   *
   * @return the command name as a {@code String}
   */
  public String getCommandName() {
    return commandName;
  }

  /**
   * Gets the command associated with this enum constant.
   *
   * <p>This method dynamically instantiates the command class by invoking the default
   * constructor of the command.</p>
   *
   * @return an instance of the {@link Command} associated with this enum constant
   */
  private Command instantiateCommand(String args) {
    try {
      return commandClass.getDeclaredConstructor(String.class).newInstance(args);
    } catch (NoSuchMethodException e) {
      throw new InternalError(
          "Internal Error: The specified command" + commandName + " has no String constructor.");
    } catch (InvocationTargetException e) {
      throw new IllegalArgumentException(
          "There was an error in constructor invocation of command : " + e.getCause().getMessage());
    } catch (InstantiationException e) {
      throw new InternalError("Internal Error: The specified command cannot be instantiated.");
    } catch (IllegalAccessException e) {
      throw new InternalError("Internal Error: The specified command cannot be accessed.");
    }
  }
}
