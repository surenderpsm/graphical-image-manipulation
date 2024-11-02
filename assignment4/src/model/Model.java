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
public class Model implements ModelRunner, ImageCacheProvider {

  private CommandEnum commandClass = CommandEnum.NONE;
  private final Cache cache = new Cache();
  @Override
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

  @Override
  public int[][][] getImage(String name) throws NoSuchElementException {
    try {
      return cache.get(name).getImageArray();
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException(e.getMessage());
    }
  }

  @Override
  public void setImage(int[][][] image, String name) {
    cache.set(name, new Image(image));
  }

  /**
   * Executes the command associated with this model instance using the provided arguments.
   *
   * @param args String arguments to the command being executed.
   */
  private void runCommand(String args) {
    commandClass.executeCommandWith(args, cache);
  }
}
