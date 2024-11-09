package view.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ImageMenu extends JMenu {

  public ImageMenu() {
    super("Image");

    // Add Transform submenu
    add(new TransformSubMenu());

    // Add Compress item
    add(new JMenuItem("Compress"));
  }

  private static class TransformSubMenu extends JMenu {

    public TransformSubMenu() {
      super("Transform");

      // Add transform options
      add(new JMenuItem("Horizontal Flip"));
      add(new JMenuItem("Vertical Flip"));
    }
  }
}

