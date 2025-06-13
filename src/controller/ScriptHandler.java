package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* @deprecated */

/**
 * The {@code ScriptHandler} class is responsible for reading and parsing a script file. The script
 * contains a series of commands that are executed sequentially. It filters out comments (lines
 * starting with "#") and empty lines, returning only the relevant commands. This class reads the
 * script from a specified file path, processes the content line by line, and returns a list of
 * valid commands for further execution.
 */
class ScriptHandler {

  private final File file;

  /**
   * Constructs a {@code ScriptHandler} with the specified path to the script file.
   *
   * @param path The path to the script file that contains the commands to be executed.
   */
  public ScriptHandler(String path) {
    file = new File(path);
  }

  /**
   * Reads the script file and returns a list of valid commands. This method processes each line of
   * the file, ignoring comments (lines starting with "#") and empty lines. It returns a list of
   * commands to be executed.
   *
   * @return A list of commands extracted from the script file.
   * @throws IllegalArgumentException If the script file is not found at the specified path.
   */
  public List<String> getCommands() {
    List<String> commands = new ArrayList<>();
    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNextLine()) {
        String line = sc.nextLine().trim();
        if (!line.startsWith("#") && !line.isEmpty()) {
          commands.add(line);
        }
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found: The specified path is invalid.");
    }
    return commands;
  }
}

