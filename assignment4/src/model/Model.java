package model;

import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import model.command.Command;
import model.command.CommandEnum;
import model.command.CommandStatus;

/**
 * The {@code Model} class serves as the entry point to the entire model in the MVC architecture. It
 * manages command execution, image retrieval, and storage while interfacing with the
 * {@link CommandEnum} enum to determine which {@link Command} to execute with. The {@code Model}
 * interacts with an internal image cache via {@link Image.Cache}, and maintains an internal status
 * flag to track the success of operations.
 * <p>
 * All interactions between the controller and the underlying model must go through this class,
 * ensuring that the controller only accesses the model through this entry point.
 * </p>
 * <h3>Usage Examples:</h3>
 * <pre>
 *   // in the controller:
 *
 *   // to run a command and get its status:
 *   boolean status = new Model("blur koala koala_blurred").getStatus();
 *
 *   // Gets an image from the model's Image.Cache
 *   int[][][] image = new Model().getImage("koala");
 *
 *   // Loads an Image into the cache.
 *   boolean status = new Model().setImage(image, 'rabbit').getStatus();
 * </pre>
 *
 * @see Command
 * @see CommandEnum
 * @see CommandStatus
 */
public class Model {

  private final CommandEnum commandClass;
  private boolean status = false;
  private String args;

  /**
   * Constructs a {@code Model} instance by matching the input string to a corresponding command in
   * the {@link CommandEnum} enum. If the command is found, it is assigned to the model, otherwise, an
   * {@link UnsupportedOperationException} is thrown.
   *
   * <p>
   * Status updates can be tracked with {@link Model#getStatus()}.
   * </p>
   *
   * @param commandLine the name of the command to be executed appended to the arguments passed.
   * @throws UnsupportedOperationException if the input command is not found in the {@link CommandEnum}
   *                                       enum
   */
  public Model(String commandLine) throws UnsupportedOperationException {
    String[] tokens = commandLine.split(" ", 1);
    String command = tokens[0];
    args = tokens[1];
    for (CommandEnum c : CommandEnum.values()) {
      if (c.getCommandName().equals(command)) {
        commandClass = c;
        status = true;
        execute();
        return;
      }
    }
    status = false;
    throw new UnsupportedOperationException("User Error: Input command not found.");
  }

  /**
   * Default constructor that initializes the model with a default command (I/O).
   * <br>
   * <br>
   * <strong>
   * Default mode is used to load or save an image into the model.
   * </strong>
   */
  public Model() {
    commandClass = CommandEnum.IO;
    status = true;
  }

  /**
   * Retrieves the image data for a specified image name from the cache. If the image is not found,
   * a {@link NoSuchElementException} is thrown.
   *
   * <p>
   * The image data is returned as a 3D array formatted as
   * {@code int[width][height][num_channels]}:
   * <ul>
   *   <li>width: the width of the image</li>
   *   <li>height: the height of the image</li>
   *   <li>num_channels: the number of color channels (e.g., RGB, RGBA)</li>
   * </ul>
   * </p>
   *
   * @param name the name of the image to retrieve
   * @return a 3D array representing the image data
   * @throws NoSuchElementException if the specified image is not found in the cache
   */
  public int[][][] getImage(String name) throws NoSuchElementException {
    try {
      return Image.Cache.get(name).getImageArray();
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException(e.getMessage());
    }
  }

  /**
   * Stores an image in the cache with the specified name. This method updates the status flag to
   * {@code true} once the image is successfully set.
   * <p>
   * Status updates can be tracked with {@link Model#getStatus()}.
   * </p>
   *
   * @param image a 3D array representing the image data in the format
   *              {@code int[width][height][num_channels]}
   * @param name  the name under which the image will be stored
   */
  public void setImage(int[][][] image, String name) {
    status = false;
    Image.Cache.set(name, new Image(image));
    status = true;
  }

  /**
   * Executes the command associated with this model instance using the provided arguments. The
   * method updates the status flag based on the execution result.
   * <p>
   * Status updates can be tracked with {@link Model#getStatus()}.
   * </p>
   *
   * @throws UnsupportedOperationException if the command cannot be instantiated, invoked, or
   *                                       accessed due to internal errors
   */
  private void execute() {
    status = false;
    try {
      Command command = commandClass.getCommand();
      command.executeWith(args);
      if (command.status() == CommandStatus.SUCCESS) {
        status = true;
      }
    } catch (NoSuchMethodException e) {
      throw new InternalError("Internal Error: The specified command has no constructors.");
    } catch (InvocationTargetException e) {
      throw new UnsupportedOperationException(
          "Internal Error: There was an error in constructor invocation of command  : "
              + e.getCause().getMessage());
    } catch (InstantiationException e) {
      throw new InternalError("Internal Error: The specified command cannot be instantiated.");
    } catch (IllegalAccessException e) {
      throw new InternalError("Internal Error: The specified command cannot be accessed.");
    }
  }

  /**
   * Returns the current status of the model. The status reflects whether the last command or
   * operation was successful.
   * <p> <strong>
   * Status update is not available for {@link Model#getImage(String)}
   * </strong> </p>
   *
   * @return {@code true} if the last operation was successful, {@code false} otherwise
   */
  public boolean getStatus() {
    return status;
  }
}
