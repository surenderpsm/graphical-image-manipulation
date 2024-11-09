package view.menu;


import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FilterMenu extends JMenu {

  public FilterMenu() {
    super("Filter");

    // Add blur and sharpen options
    add(new JMenuItem("Blur"));
    add(new JMenuItem("Sharpen"));
  }
}
