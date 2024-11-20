package view.gui.utlis;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFileChooser {
  public FileChooser() {
    super();
    setCurrentDirectory(new File(System.getProperty("user.dir")));
    setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "ppm"));
  }
}
