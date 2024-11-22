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
public interface Features extends ModelSharer {

  /**
   * Retrieves the set of available command strings that the view can use to invoke actions in
   * the controller.
   *
   * @return a set of strings representing the names of the available commands.
   */
  Set<String> getCommandNames();

  /**
   * Retrieves a mandated argument wrapper for a specified command. This wrapper contains the
   * necessary arguments that must be passed along with the command.
   *
   * @param commandName the name of the command for which arguments are to be fetched.
   * @return a {@link MandatedArgWrapper} containing the required arguments for the command.
   */
  MandatedArgWrapper getMandatedArgs(String commandName);

  /**
   * Loads the given image from the specified file path and passes it to the model for further
   * processing. An alias is also provided to reference the image in the cache.
   *
   * @param file the file from which the image will be loaded.
   * @param alias a string alias used to refer to the loaded image.
   */
  void loadImage(File file, String alias);

  /**
   * Saves the image associated with the given alias to the specified file path.
   *
   * @param file the file path where the image will be saved.
   * @param alias the alias of the image to be saved.
   */
  void saveImage(File file, String alias);

  /**
   * Invokes a command using a string identifier and an {@link ArgumentWrapper} containing the
   * arguments for the command.
   *
   * @param command the name of the command to invoke.
   * @param args an {@link ArgumentWrapper} containing the arguments for the command.
   */
  void invokeCommand(String command, ArgumentWrapper args);

  /**
   * Exits the application, performing any necessary cleanup before shutdown.
   */
  void exitApplication();
}
