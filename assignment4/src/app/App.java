package app;

import controller.Controller;
import model.Model;

public class App {

  public static void main(String[] args) {
    Controller controller = new Controller(System.in, System.out);
    controller.run(new Model());
  }

}
