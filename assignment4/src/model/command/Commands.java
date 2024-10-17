package model.command;


import java.lang.reflect.InvocationTargetException;
import model.ArgumentWrapper;

/**
 * The {@code Commands} enum represents a collection of command types for various operations in the
 * image processing application. Each enum constant is associated with a specific command class that
 * implements the {@code Command} interface, a string representation of the command name and an
 * array of {@code ArgTypes} representing of the expected argument signature.
 *
 * <p>This enum provides a method to execute commands with arguments, allowing
 * dynamic instantiation of command objects at runtime.</p>
 *
 * <h2>Example Usage:</h2>
 * <pre>
 *     Commands cmd = Commands.LOAD;
 *     cmd.executeWith(arguments);
 * </pre>
 *
 * @see Command
 */
public enum Commands {
  LOAD(Load.class, "load"),
  SAVE(Save.class, "save"),
  BRIGHTEN(Brighten.class, "brighten"),
  VERTICAL_FLIP(VerticalFlip.class, "vertical-flip"),
  HORIZONTAL_FLIP(HorizontalFlip.class, "horizontal-flip"),
  RGB_SPLIT(RGBSplit.class, "rgb-split"),
  RGB_COMBINE(RGBCombine.class, "rgb-combine"),
  BLUR(Blur.class, "blur"),
  SHARPEN(Sharpen.class, "sharpen"),
  SEPIA(Sepia.class, "sepia");

  private final Class<? extends Command> commandClass;
  private final String commandName;

  /**
   * Constructs a {@code Commands} enum constant with the specified command class and name.
   *
   * @param command the class representing the command associated with this enum constant
   * @param commandName the string name of the command
   */
  Commands(Class<? extends Command> command, String commandName) {
    this.commandClass = command;
    this.commandName = commandName;
  }

  /**
   * Executes the command associated with this enum constant using the provided arguments.
   *
   * <p>This method dynamically instantiates the command class using reflection,
   * passing the provided {@code ArgumentWrapper} to the constructor of the command, and then
   * executes the command.</p>
   *
   * @param args an {@code ArgumentWrapper} containing the arguments required by the command
   * @return {@code true} if the command executed successfully, {@code false} otherwise
   * @throws NoSuchMethodException     if the command class does not have a constructor that takes
   *                                   an {@code ArgumentWrapper}
   * @throws InvocationTargetException if the constructor or command execution throws an exception
   * @throws InstantiationException    if the command class cannot be instantiated
   * @throws IllegalAccessException    if the command class or its constructor is not accessible
   */
  public boolean executeWith(ArgumentWrapper args)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Command c = commandClass.getDeclaredConstructor(ArgumentWrapper.class).newInstance(args);
    c.execute();
    return c.status();
  }

  /**
   * Returns the name of the command associated with this enum constant.
   *
   * <p>This name is used for identifying the command in user input or configuration files.</p>
   *
   * @return the command name as a {@code String}
   */
  public String getCommandName() {
    return commandName;
  }

}
