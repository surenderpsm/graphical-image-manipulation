package controller;

import controller.imagehandler.ImageHandler;
import controller.imagehandler.ImageHandlerSelector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Model;

/**
 * The {@code IOHandler} class is responsible for executing  out IO related commands.
 */
public class IOHandler {
  Model model;
  String name;
  String path;
  ImageHandler ih;
  String command;
  public IOHandler(Model model, String command, String args) throws IOException {
    this.model = model;
    this.command = command;

    if (!command.equals("load") && !command.equals("save")) {
      throw new IllegalArgumentException("Invalid command. Supported IO commands are: load, save");
    }

    String[] argsArray = parseArgs(args);
    path = argsArray[0];
    name = argsArray[1];

    createParentDirectories();

    ih = findMatchingImageHandler(path);
    if (ih == null) {
      throw new IllegalArgumentException(
          "No matching ImageHandler found for the provided arguments.");
    }

    commandSelector();
  }

  private void createParentDirectories() throws IOException {
    // Create a File object for the path
    File file = new File(path);

    // Get the parent directory of the file
    File parentDir = file.getParentFile();

    // Check if the parent directory exists; if not, create it
    if (parentDir != null && !parentDir.exists()) {
      // Creates the directory including any necessary but nonexistent parent directories
      if(!parentDir.mkdirs()){
        // directory creation failed.
        throw new IOException("Couldn't create parent directory "+parentDir);
      }
    }
  }
  private void commandSelector() throws IOException {
    switch (command) {
      case "load":
        model.setImage(ih.loadImage(), name);
        break;
      case "save":
        if (model.isHistogram(name)) {
          ih.saveImage(new HistogramGenerator(model.getHistogram(name)).getImage());
        }
        else {
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

  // Helper method to parse arguments
  private String[] parseArgs(String args) {
    String[] argsArray = args.split(" ");
    if (argsArray.length != 2) {
      throw new IllegalArgumentException("Invalid arguments. Expected format: <path> <name>");
    }
    return argsArray;
  }

  // Helper method to find the matching ImageHandler
  private ImageHandler findMatchingImageHandler(String path) {
    for (ImageHandlerSelector handler : ImageHandlerSelector.values()) {
      if (handler.isMatchingImageHandler(path)) {
        return handler.createImageHandler(path);
      }
    }
    return null;  // Return null if no matching handler found
  }

}