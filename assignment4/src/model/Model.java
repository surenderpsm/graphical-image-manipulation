package model;

import java.util.NoSuchElementException;
import model.command.CommandEnum;

/**
 * The {@code Model} class serves as the entry point to the entire model in the MVC architecture. It
 * manages command execution, image retrieval, and storage while interfacing with the
 * {@link CommandEnum} enum to determine which command to execute with. The {@code Model} interacts
 * with an internal image cache via {@link Image.Cache}, and maintains an internal status flag to
 * track the success of operations.
 * <p>
 * All interactions between the controller and the underlying model must run through this class,
 * ensuring that the controller only accesses the model through this entry point.
 * </p>
 * <h3>Usage Examples:</h3>
 * <pre>
 *   // In the main:
 *   Model model = new Model();
 *
 *   // in the controller:
 *
 *   // to run a command:
 *   model.execute("blur koala koala_blurred");
 *
 *   // Gets an image from the model's Image.Cache
 *   int[][][] image = model.getImage("koala");
 *
 *   // Loads an Image into the cache.
 *   model.setImage(image, 'rabbit');
 * </pre>
 *
 * @see CommandEnum
 */
public class Model {

  private CommandEnum commandClass = CommandEnum.NONE;

  /**
   * Execute a {@code Model} command by passing a {@code command} and {@code arguments}.
   *
   * @param command the name of the command.
   * @param args    a string of arguments for the command.
   * @throws UnsupportedOperationException if the input command is not found in the
   *                                       {@link CommandEnum} enum
   */
  public void execute(String command, String args) throws UnsupportedOperationException {
    for (CommandEnum c : CommandEnum.values()) {
      if (c.getCommandName().equals(command)) {
        commandClass = c;
        runCommand(args);
        return;
      }
    }
    throw new UnsupportedOperationException("User Error: Input command not found.");
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
   *
   * @param image a 3D array representing the image data in the format
   *              {@code int[width][height][num_channels]}
   * @param name  the name under which the image will be stored
   */
  public void setImage(int[][][] image, String name) {
    Image.Cache.set(name, new Image(image));
  }

  /**
   * Executes the command associated with this model instance using the provided arguments.
   *
   * @param args String arguments to the command being executed.
   */
  private void runCommand(String args) {
    commandClass.executeCommandWith(args);
  }
}
