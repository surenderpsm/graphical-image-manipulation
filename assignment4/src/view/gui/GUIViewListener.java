package view.gui;

import java.io.File;
import utils.arguments.ArgumentWrapper;
import view.ViewListener;

public interface GUIViewListener extends ViewListener {

  void onLoadImage(File file);

  void onSaveImage(File file);

  void onCommandExecuted(String command, ArgumentWrapper args);
}
