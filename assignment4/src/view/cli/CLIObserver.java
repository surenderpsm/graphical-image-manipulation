package view.cli;

import view.ViewObserver;
import controller.viewhandler.CLIAdapter;

/**
 * The CLIObserver interface represents an observer for the CLI view.
 * <p>
 * This interface extends {@link ViewObserver} and is used by the {@link CLIAdapter}
 * to listen for user input from the command line interface (CLI). It defines
 * a method that is triggered when the user enters input, enabling the application
 * to process the input and perform the corresponding actions.
 * </p>
 */
public interface CLIObserver extends ViewObserver {

  /**
   * This method is called by the {@link CLIAdapter} to notify the system of
   * user input from the CLI.
   *
   * @param input the string input provided by the user in the CLI.
   *              It can represent commands, arguments, or other data entered
   *              by the user.
   */
  void onUserInput(String input);

}
