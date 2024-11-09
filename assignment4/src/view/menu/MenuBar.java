package view.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

  public MenuBar() {
    add(new FileMenu());
    add(new JMenu("Edit"));
    add(new ImageMenu());
    add(new ColorMenu());
    add(new FilterMenu());
    add(new JMenu("Help"));
    add(new JMenu("About"));
  }
}
