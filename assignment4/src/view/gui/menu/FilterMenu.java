package view.gui.menu;


import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import view.gui.SubComponentBinder;

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
