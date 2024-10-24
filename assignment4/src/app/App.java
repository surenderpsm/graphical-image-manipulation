package app;

import controller.Controller;
import model.Model;

/**
 * represents our applications main method containing class.
 */

public class App {

  public static void main(String[] args) {
    Controller controller = new Controller(System.in, System.out);
    controller.run(new Model());
  }

}
