package view.gui;

import java.io.File;
import utils.arguments.ArgumentWrapper;
import view.ViewObserver;

/**
 * Observes changes from the GUIImpl Facade class.
 */
public interface GUIObserver extends ViewObserver {

  void onLoadImage(File file);

  void onSaveImage(File file);

  void onCommandExecuted(String command, ArgumentWrapper args);
}
