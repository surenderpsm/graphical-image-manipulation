/* @formatter:off */
package model.command;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import model.Cache;
import utils.arguments.ArgumentType;
import utils.arguments.ArgumentWrapper;
import utils.arguments.Signature;


/**
 * The {@code CommandFactory} enum serves as the sole public entry point for the command package,
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
 * The enum provides the {@link CommandFactory#executeCommandWith(ArgumentWrapper, Cache)} method to
 * run commands with the provided arguments, abstracting the details of command instantiation.
 * </p>
 *
 * <h3>Example Usage:</h3>
 * <pre>
 *     // Executes the brighten command with the specified arguments
 *     CommandFactory.BRIGHTEN.executeCommandWith("koala new-koala-brighten");
 * </pre>
 *
 * @see Command
 */
public enum CommandFactory {
  NONE(null,
       "",
       Signature.EMPTY),
  BRIGHTEN(Brighten.class,
           "brighten",
           Signature.define(ArgumentType.INT,
                            ArgumentType.IMAGE,
                            ArgumentType.STRING)),
  VERTICAL_FLIP(VerticalFlip.class,
                "vertical-flip",
                Signature.define(ArgumentType.IMAGE,
                                 ArgumentType.STRING)),
  HORIZONTAL_FLIP(HorizontalFlip.class,
                  "horizontal-flip",
                  Signature.define(ArgumentType.IMAGE,
                                   ArgumentType.STRING)),
  RGB_SPLIT(RGBSplit.class,
            "rgb-split",
            Signature.define(ArgumentType.IMAGE,
                             ArgumentType.STRING,
                             ArgumentType.STRING,
                             ArgumentType.STRING)),
  RGB_COMBINE(RGBCombine.class,
              "rgb-combine",
              Signature.define(ArgumentType.STRING,
                               ArgumentType.IMAGE,
                               ArgumentType.IMAGE,
                               ArgumentType.IMAGE)),
  BLUR(Blur.class,
       "blur",
       Signature.define(ArgumentType.IMAGE,
                        ArgumentType.STRING)),
  SHARPEN(Sharpen.class,
          "sharpen",
          Signature.define(ArgumentType.IMAGE,
                           ArgumentType.STRING)),
  SEPIA(Sepia.class,
        "sepia",
        Signature.define(ArgumentType.IMAGE,
                         ArgumentType.STRING)),
  RED_COMPONENT(RedComponent.class,
                "red-component",
                Signature.define(ArgumentType.IMAGE,
                                 ArgumentType.STRING)),
  GREEN_COMPONENT(GreenComponent.class,
                  "green-component",
                  Signature.define(ArgumentType.IMAGE,
                                   ArgumentType.STRING)),
  BLUE_COMPONENT(BlueComponent.class,
                 "blue-component",
                 Signature.define(ArgumentType.IMAGE,
                                  ArgumentType.STRING)),
  VALUE_COMPONENT(ValueComponent.class,
                  "value-component",
                  Signature.define(ArgumentType.IMAGE,
                                   ArgumentType.STRING)),
  LUMA_COMPONENT(LumaComponent.class,
                 "luma-component",
                 Signature.define(ArgumentType.IMAGE,
                                  ArgumentType.STRING)),
  INTENSITY_COMPONENT(IntensityComponent.class,
                      "intensity-component",
                      Signature.define(ArgumentType.IMAGE,
                                       ArgumentType.STRING)),
  HISTOGRAM(Histogram.class,
            "histogram",
            Signature.define(ArgumentType.IMAGE,
                             ArgumentType.STRING)),
  COMPRESS(Compress.class,
           "compress",
           Signature.define(ArgumentType.INT,
                            ArgumentType.IMAGE,
                            ArgumentType.STRING)),
  GRAYSCALE(LumaComponent.class,
            "grayscale",
            Signature.define(ArgumentType.IMAGE,
                             ArgumentType.STRING)),
  COLOR_CORRECTION(ColorCorrection.class,
                   "color-correct",
                   Signature.define(ArgumentType.IMAGE,
                                    ArgumentType.STRING)),
  LEVELS_ADJUSTMENT(LevelsAdjust.class,
                    "levels-adjust",
                    Signature.define(ArgumentType.INT,
                                     ArgumentType.INT,
                                     ArgumentType.INT,
                                     ArgumentType.IMAGE,
                                     ArgumentType.STRING)),
  ;


  private final Class<? extends Command> commandClass;
  private final String commandName;
  private final Signature signature;

  /**
   * Constructs a {@code CommandFactory} enum constant with the specified command class and name.
   *
   * @param command     the class representing the command associated with this enum constant
   * @param commandName the string name of the command
   */
  CommandFactory(Class<? extends Command> command, String commandName, Signature signature) {
    this.commandClass = command;
    this.commandName = commandName;
    this.signature = signature;
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
  public void executeCommandWith(ArgumentWrapper args, Cache cache) {
    if (this == CommandFactory.NONE) {
      throw new IllegalStateException("Illegal state: No command has been assigned to Model.");
    }
    Command
        c =
        instantiateCommand(args,
                           cache);
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

  public Signature getArgumentSignature() {
    return signature;
  }

  public static Map<String, Signature> getSignatureMap() {
    Map<String, Signature> map = new HashMap<>();
    for (CommandFactory command : CommandFactory.values()) {
      map.put(command.getCommandName(),
              command.getArgumentSignature());
    }
    return map;
  }

  /**
   * Gets the command associated with this enum constant.
   *
   * <p>This method dynamically instantiates the command class by invoking the default
   * constructor of the command.</p>
   *
   * @return an instance of the {@link Command} associated with this enum constant
   */
  private Command instantiateCommand(ArgumentWrapper args, Cache cache) {
    try {
      return commandClass.getDeclaredConstructor(ArgumentWrapper.class,
                                                 Cache.class).newInstance(args,
                                                                          cache);
    } catch (NoSuchMethodException e) {
      throw new InternalError(
          "Internal Error: The specified command" + commandName + " has no ArgumentWrapper and "
              + "Cache constructor.");
    } catch (InvocationTargetException e) {
      throw new IllegalArgumentException("ERROR : " + e.getCause().getMessage());
    } catch (InstantiationException e) {
      throw new InternalError("Internal Error: The specified command cannot be instantiated.");
    } catch (IllegalAccessException e) {
      throw new InternalError("Internal Error: The specified command cannot be accessed.");
    }
  }
}
