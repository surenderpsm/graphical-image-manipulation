package view.gui.menu;

import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import view.gui.SubComponentBinder;
import view.gui.ViewComponentListener;
import view.gui.utlis.FileChooser;

public class FileMenu extends JMenu {

  ViewComponentListener listener;

  public FileMenu(SubComponentBinder binder) {
    super("File");
    listener = binder.getViewComponentListener();
    setMnemonic(KeyEvent.VK_F);

    JMenuItem openItem = new JMenuItem("Open");
    openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    openItem.addActionListener(e -> {
      FileChooser fc = new FileChooser();
      int result = fc.showOpenDialog(this);
      listener.loadImage((result == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null);
    });

    JMenuItem saveItem = new JMenuItem("Save");
    saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    binder.addToDisabledByDefault(saveItem);
    saveItem.addActionListener(e -> {

      FileChooser fc = new FileChooser();
      int result = fc.showSaveDialog(this);

      listener.saveImage((result == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null);
    });

    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> {
      listener.quit();
    });

    // Add menu items
    add(openItem);
    add(saveItem);
  }

  private static class ExportSubMenu extends JMenu {

    public ExportSubMenu() {
      super("Export as");

      // Add export formats
      add(new JMenuItem("PPM"));
      add(new JMenuItem("PNG"));
      add(new JMenuItem("JPG"));
      this.setEnabled(false);
    }
  }
}

