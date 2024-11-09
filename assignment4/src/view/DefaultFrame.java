package view;

import javax.swing.JFrame;
import view.menu.MenuBar;

public class DefaultFrame extends JFrame {

  public DefaultFrame() {
    setTitle("GIME - Graphical Image Manipulation Enhancement");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create and set the custom menu bar
    setJMenuBar(new MenuBar());

    setVisible(true);
  }
}
