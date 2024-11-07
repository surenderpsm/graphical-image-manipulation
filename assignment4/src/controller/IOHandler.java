package controller;

import controller.imagehandler.ImageHandler;
import controller.imagehandler.ImageHandlerSelector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Model;

/**
 * The {@code IOHandler} class is responsible for executing input and output (IO) related commands
 * such as loading and saving images. It processes commands that interact with the file system,
 * ensuring the appropriate handlers are selected based on the file path and command type.
 *
 * <p>This class supports two main commands: {@code load} and {@code save}, which load an image into
 * the model or save an image from the model to the file system, respectively.</p>
 *
 * <h2>Example Usage:</h2>
 * <pre>
 *     IOHandler ioHandler = new IOHandler(model, "load", "path/to/image.png imageName");
 *     // Loads the image into the model with the name "imageName"
 * </pre>
 */
public class IOHandler {
  private final Model model;
  private final String name;
  private final String path;
  private final ImageHandler ih;
  private final String command;

  /**
   * Constructs an {@code IOHandler} that processes a specified command with given arguments.
   * This method parses the command and arguments, determines the appropriate image handler,
   * and invokes the appropriate action based on the command (either loading or saving an image).
   *
   * @param model The model in which the image will be loaded or saved.
   * @param command The command to be executed, either {@code load} or {@code save}.
   * @param args The arguments associated with the command, containing the path and image name.
   * @throws IOException If an error occurs during the loading or saving of the image.
   * @throws IllegalArgumentException If the command is invalid or arguments are incorrectly formatted.
   * @throws UnsupportedOperationException If a suitable {@link ImageHandler} is not found.
   */
  public IOHandler(Model model, String command, String args) throws IOException {
    this.model = model;
    this.command = command;

    // Validate the command type
    if (!command.equals("load") && !command.equals("save")) {
      throw new IllegalArgumentException("Invalid command. Supported IO commands are: load, save");
    }

    // Parse the command arguments
    String[] argsArray = parseArgs(args);
    path = argsArray[0];
    name = argsArray[1];

    // Ensure the parent directories of the path exist
    createParentDirectories();

    // Find and initialize the corresponding ImageHandler
    ih = findMatchingImageHandler(path);
    if (ih == null) {
      throw new UnsupportedOperationException(
          "No matching ImageHandler found for the provided file path.");
    }

    // Execute the appropriate command (load or save)
    commandSelector();
  }

  /**
   * Creates the parent directories of the specified path if they do not already exist.
   *
   * @throws IOException If the directories cannot be created.
   */
  private void createParentDirectories() throws IOException {
    File file = new File(path);
    File parentDir = file.getParentFile();

    if (parentDir != null && !parentDir.exists()) {
      if (!parentDir.mkdirs()) {
        throw new IOException("Failed to create parent directory: " + parentDir);
      }
    }
  }

  /**
   * Selects and executes the appropriate action based on the specified command.
   * This method processes either the {@code load} or {@code save} command and delegates
   * the task to the corresponding image handler.
   *
   * @throws IOException If an error occurs while loading or saving the image.
   * @throws IllegalArgumentException If the model's histogram or image cannot be found.
   */
  private void commandSelector() throws IOException {
    switch (command) {
      case "load":
        model.setImage(ih.loadImage(), name);
        break;
      case "save":
        if (model.isHistogram(name)) {
          ih.saveImage(new HistogramGenerator(model.getHistogram(name)).getImage());
        } else {
          try {
            ih.saveImage(model.getImage(name));
          } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
        }
        break;
      default:
        throw new UnsupportedOperationException("Unsupported command: " + command);
    }
  }

  /**
   * Parses the arguments string and splits it into an array containing the file path and image name.
   *
   * @param args The arguments string containing the file path and image name.
   * @return A string array containing the path at index 0 and the image name at index 1.
   * @throws IllegalArgumentException If the arguments string is not properly formatted.
   */
  private String[] parseArgs(String args) {
    String[] argsArray = args.split(" ");
    if (argsArray.length != 2) {
      throw new IllegalArgumentException("Invalid arguments. Expected format: <path> <name>");
    }
    return argsArray;
  }

  /**
   * Finds the appropriate {@code ImageHandler} based on the provided file path.
   * It iterates through the available {@code ImageHandlerSelector} options to find a matching handler.
   *
   * @param path The file path to the image.
   * @return The matching {@code ImageHandler}, or {@code null} if no handler is found.
   */
  private ImageHandler findMatchingImageHandler(String path) {
    for (ImageHandlerSelector handler : ImageHandlerSelector.values()) {
      if (handler.isMatchingImageHandler(path)) {
        return handler.createImageHandler(path);
      }
    }
    return null;
  }
}
