package view.cli;

import view.ViewObserver;

public interface CLIObserver extends ViewObserver {

  /**
   * This method is used by the CLIAdapter to listen for input.
   *
   * @param input The string input is passed on.
   */
  void onUserInput(String input);

}
