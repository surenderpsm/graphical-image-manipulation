package view.gui.frame.menu;

import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import view.gui.frame.SubComponentBinder;
import view.gui.ComponentObserver;
import view.gui.frame.FileChooser;

/**
 * The FileMenu class represents a menu in the GUI for file operations.
 * <p>
 * This menu includes options for opening, saving, and quitting. It provides accelerators
 * for opening and saving files using key combinations (Ctrl+O and Ctrl+S).
 * </p>
 */
public class FileMenu extends JMenu {

  ComponentObserver listener;

  /**
   * Constructs a FileMenu with options to open, save, and exit.
   *
   * @param binder the {@link SubComponentBinder} that provides access to a listener
   *               for handling user actions such as loading and saving images.
   */
  public FileMenu(SubComponentBinder binder) {
    super("File");
    listener = binder.getViewComponentListener();
    setMnemonic(KeyEvent.VK_F); // Set the mnemonic for the File menu

    // Open menu item
    JMenuItem openItem = new JMenuItem("Open");
    openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    openItem.addActionListener(e -> {
      FileChooser fc = new FileChooser();
      int result = fc.showOpenDialog(this);
      listener.loadImage((result == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null);
    });

    // Save menu item
    JMenuItem saveItem = new JMenuItem("Save");
    saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    binder.addToDisabledByDefault(saveItem); // Initially disable save
    saveItem.addActionListener(e -> {
      FileChooser fc = new FileChooser();
      int result = fc.showSaveDialog(this);
      listener.saveImage((result == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null);
    });

    // Add menu items
    add(openItem);
    add(saveItem);
  }
}
