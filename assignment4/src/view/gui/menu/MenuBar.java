package view.gui.menu;

import javax.swing.JMenuBar;
import view.gui.SubComponentBinder;

public class MenuBar extends JMenuBar {

  public MenuBar(SubComponentBinder binder) {

    add(new FileMenu(binder));
    add(new ImageMenu(binder));
    add(new ColorMenu(binder));
    add(new FilterMenu(binder));
  }
}
