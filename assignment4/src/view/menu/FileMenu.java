package view.menu;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class FileMenu extends JMenu {

  public FileMenu() {
    super("File");
    setMnemonic(KeyEvent.VK_F);

    // Add menu items
    add(new NewItem());
    add(new OpenItem());
    add(new SaveItem());

    // Add the Export submenu
    add(new ExportSubMenu());
  }

  private static class NewItem extends JMenuItem {

    public NewItem() {
      super("New");
      setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
    }
  }

  private static class OpenItem extends JMenuItem {

    public OpenItem() {
      super("Open");
      setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    }
  }

  private static class SaveItem extends JMenuItem {

    public SaveItem() {
      super("Save");
      setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    }
  }

  private static class ExportSubMenu extends JMenu {

    public ExportSubMenu() {
      super("Export as");

      // Add export formats
      add(new JMenuItem("PPM"));
      add(new JMenuItem("PNG"));
      add(new JMenuItem("JPG"));
    }
  }
}

