package view.gui;

import java.io.File;
import utils.arguments.ArgumentWrapper;
import view.ViewObserver;

/**
 * The {@code GUIObserver} interface extends {@link ViewObserver} and is responsible
 * for observing changes or inputs made by the user in the GUI. Implementations of this
 * interface will handle events such as loading and saving images, and executing commands
 * with specific arguments.
 */
public interface GUIObserver extends ViewObserver {

  /**
   * Called when the user loads an image file through the GUI.
   *
   * @param file the image file selected by the user
   */
  void onLoadImage(File file);

  /**
   * Called when the user saves an image file through the GUI.
   *
   * @param file the image file to be saved
   */
  void onSaveImage(File file);

  /**
   * Called when a command is executed in the GUI.
   *
   * @param command the name of the command executed
   * @param args    the {@link ArgumentWrapper} containing the arguments for the command
   */
  void onCommandExecuted(String command, ArgumentWrapper args);
}
