package model;

import model.command.CommandEnum;

/**
 * class that depicts the model runner. model must implement this class.
 * each model must have an execute method.
 */

public interface ModelRunner {

  /**
   * Execute a {@code Model} command by passing a {@code command} and {@code arguments}.
   *
   * @param command the name of the command.
   * @param args    a string of arguments for the command.
   * @throws UnsupportedOperationException if the input command is not found in the
   *                                       {@link CommandEnum} enum
   */
  void execute(String command, String args) throws UnsupportedOperationException;

}
