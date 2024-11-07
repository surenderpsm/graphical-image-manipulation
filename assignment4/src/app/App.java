package app;

import controller.Controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Model;

/**
 * represents our applications main method containing class.
 */

public class App {

  public static void main(String[] args) {
    String filePath = null;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-file") && i + 1 < args.length) {
        filePath = args[i + 1];
        break;
      }
    }
    if (filePath != null) {
      // If a file path is specified, use it as input
      System.out.println("Loading file: " + filePath);
      try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
        new Controller(fileInputStream, System.out).run(new Model());
      } catch (FileNotFoundException e) {
        System.out.println("File not found: " + filePath);
      } catch (IOException e) {
        System.out.println("Error reading file: " + filePath);
      }
    }
    else {
      // If no file path is specified, use System.in as input
      new Controller(System.in, System.out).run(new Model());
    }
  }

}
