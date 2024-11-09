package app;

import controller.Controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.SwingUtilities;
import model.Model;
import view.DefaultFrame;

/**
 * Represents our applications main method containing class.
 */
public class App {

  /**
   * This is the main method.
   *
   * @param args cli args
   */
  public static void main(String[] args) {
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-file") && i + 1 < args.length) {
        String filePath = args[i + 1];
        // If a file path is specified, use it as input
        System.out.println("Loading file: " + filePath);
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
          new Controller(fileInputStream, System.out).run(new Model());
        } catch (FileNotFoundException e) {
          System.out.println("File not found: " + filePath);
        } catch (IOException e) {
          System.out.println("Error reading file: " + filePath);
        }
        return;
      }
      if (args[i].equals("-text")) {
        new Controller(System.in, System.out).run(new Model());
        return;
      }
    }
    SwingUtilities.invokeLater(DefaultFrame::new);
  }

}
