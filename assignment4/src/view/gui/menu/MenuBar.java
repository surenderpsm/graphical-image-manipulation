package view.gui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import view.gui.SubComponentBinder;
import view.gui.ViewComponentListener;

public class MenuBar extends JMenuBar {

  public MenuBar(SubComponentBinder binder) {

    //@todo remove useless menus
    add(new FileMenu(binder));
//    add(new JMenu("Edit"));
    add(new ImageMenu(binder));
    add(new ColorMenu(binder));
    add(new FilterMenu(binder));
//    add(new JMenu("Help"));
//    add(new JMenu("About"));
  }
}
