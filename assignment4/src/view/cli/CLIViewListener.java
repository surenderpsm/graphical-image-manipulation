package view.cli;

import view.ViewListener;

public interface CLIViewListener extends ViewListener {

  /**
   * This method is used by the CLIHandler to listen for input.
   * @param input The string input is passed on.
   */
  void onUserInput(String input);

}
