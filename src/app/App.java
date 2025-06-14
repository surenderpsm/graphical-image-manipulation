package app;

import controller.Controller;
import controller.viewhandler.CLIAdapter;
import controller.viewhandler.GUIAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import model.IModel;
import model.Model;

/**
 * Represents our applications main method containing class.
 */
public class App {

  private static IModel model;

  /**
   * This is the main method.
   *
   * @param args cli args
   */
  public static void main(String[] args) {
    model = new Model();
    Controller controller = initializeController(args);
    controller.run();
  }

  private static Controller initializeController(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-file") && i + 1 < args.length) {
        String filePath = args[i + 1];
        // If a file path is specified, use it as input
        System.out.println("Loading file: " + filePath);
        FileInputStream fileInputStream = createFileInputStream(filePath);
        // initiating controller for script.
        return new Controller(model, new CLIAdapter(fileInputStream, System.out));
      }
      if (args[i].equals("-text")) {
        // initiating controller for interactive mode.
        return new Controller(model, new CLIAdapter(System.in, System.out));
      }
    }
    // initiating controller for GUIImpl.
    return new Controller(model, new GUIAdapter());
  }

  private static FileInputStream createFileInputStream(String filePath) {
    try {
      return new FileInputStream(filePath);
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + filePath);
      return null;
    }
  }
}

