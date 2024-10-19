package model.command;


import java.lang.reflect.InvocationTargetException;

/**
 * The {@code CommandEnum} enum represents a collection of command types for various operations in
 * the image processing application. Each enum constant is associated with a specific command class
 * that implements the {@code Command} interface and a string representation of the command name.
 *
 * <p>
 * This enum provides {@link CommandEnum#getCommand()} to return an instance of the required
 * command.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 *     Command command = CommandEnum.BRIGHTEN.getCommand();
 * </pre>
 *
 * @see Command
 */
public enum CommandEnum {
  IO(null, "none"),
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
   * Gets the command associated with this enum constant.
   *
   * <p>This method dynamically instantiates the command class by invoking the default
   * constructor of the command.</p>
   *
   * @return an instance of the {@link Command} associated with this enum constant
   * @throws NoSuchMethodException     if the command class does not have a default constructor
   * @throws InvocationTargetException if the constructor or command execution throws an exception
   * @throws InstantiationException    if the command class cannot be instantiated
   * @throws IllegalAccessException    if the command class or its constructor is not accessible
   */
  public Command getCommand()
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    return commandClass.getDeclaredConstructor().newInstance();
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
}
