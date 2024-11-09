package view.menu;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {
  public MenuBar() {
    add(new JMenu("File"));
    add(new JMenu("Edit"));
    add(new JMenu("Help"));
    add(new JMenu("About"));
  }
}
