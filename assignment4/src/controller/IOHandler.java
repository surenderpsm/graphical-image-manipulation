package controller;

import controller.imagehandler.ImageHandler;
import controller.imagehandler.ImageHandlerSelector;
import java.io.IOException;
import model.Model;

/**
 * The {@code IOHandler} class is responsible for executing  out IO related commands.
 */
public class IOHandler {

  public IOHandler(Model model, String command, String args) throws IOException {
    if (!command.equals("load") && !command.equals("save")) {
      throw new IllegalArgumentException("Invalid command. Supported IO commands are: load, save");
    }

    String[] argsArray = parseArgs(args);
    String path = argsArray[0];
    String name = argsArray[1];

    ImageHandler ih = findMatchingImageHandler(path);
    if (ih == null) {
      throw new IllegalArgumentException(
          "No matching ImageHandler found for the provided arguments.");
    }

    switch (command) {
      case "load":
        model.setImage(ih.loadImage(), name);
        break;
      case "save":
        if (model.isHistogram(name)) {
          ih.saveImage(new HistogramGenerator(model.getHistogram(name)).getImage());
        }
        else {
          ih.saveImage(model.getImage(name));
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