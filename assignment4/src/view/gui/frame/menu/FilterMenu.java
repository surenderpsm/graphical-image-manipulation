package view.gui.frame.menu;


import javax.swing.JMenu;
import javax.swing.JMenuItem;
import view.gui.frame.SubComponentBinder;

public class FilterMenu extends JMenu {

  public FilterMenu(SubComponentBinder binder) {
    super("Filter");

    JMenuItem blur = new JMenuItem("Blur");
    binder.addToDisabledByDefault(blur);
    blur.addActionListener(e -> {
      binder.getViewComponentListener().blur();
    });

    JMenuItem sharpen = new JMenuItem("Sharpen");
    binder.addToDisabledByDefault(sharpen);
    sharpen.addActionListener(e -> {
      binder.getViewComponentListener().sharpen();
    });

    // Add blur and sharpen options
    add(blur);
    add(sharpen);
  }
}
