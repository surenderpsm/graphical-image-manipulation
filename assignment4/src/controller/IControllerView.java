package controller;

import java.io.File;
import java.util.Set;
import model.ModelSharer;
import utils.arguments.ArgumentWrapper;
import utils.arguments.MandatedArgWrapper;

/**
 * This interface is exposed to the view to communicate with the controller about its changes.
 * Implementations of this interface must manage a {@code Map} of aliases.
 * <br>
 * Some GUIs can handle only one image at a time, and hence they don't need to manage an alias for
 * the image it is handling. It becomes the controller's responsibility to manage the alias for that
 * image.
 */
public interface IControllerView extends ModelSharer {

  /**
   * Pass to a view handler, the available command strings.
   * @return a set of strings.
   */
  Set<String> getCommandNames();

  /**
   * Get a mandated argument wrapper to pass arguments.
   * @param commandName
   * @return
   */
  MandatedArgWrapper getMandatedArgs(String commandName);


  /**
   * Loads the given image from the provided file path and passes it to the model for further
   * processing. An additional alias is also passed to the model for referring to the image in the
   * cache.
   *
   * @param file
   * @param alias
   */
  void loadImage(File file, String alias);

//  /**
//   * Loads the image from the given file path and passes it to the model for further processing.
//   * This method generates its own alias to pass into model for reference in the cache. The
//   * controller will keep track of the aliases, and it does not concern the calling object.
//   *
//   * @param file
//   */
//  void loadImage(File file);

  /**
   * Saves the image with the given alias into the given file path.
   *
   * @param file
   */
  void saveImage(File file, String alias);


  /**
   * The calling object of this method can invoke a command by passing in a string identifier of the
   * command and an {@link ArgumentWrapper} of arguments.
   *
   * @param command
   * @param args
   */
  void invokeCommand(String command, ArgumentWrapper args);

  void exitApplication();
}
