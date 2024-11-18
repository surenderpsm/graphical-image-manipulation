package view.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import view.gui.menu.MenuBar;
import view.gui.utlis.FileChooser;

public class DefaultFrame extends JFrame implements UpdateObserver, SubComponentBinder {

  ImageViewer imagePreview;
  ImageViewer histogramPreview;
  ViewComponentListener listener;
  Set<JMenuItem> disabledByDefault = new HashSet<JMenuItem>();

  public DefaultFrame(ViewComponentListener listener) {
    this.listener = listener;
    setTitle("GIME - Graphical Image Manipulation Enhancement");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setResizable(true);

    setMinimumSize(new Dimension(800, 600));
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Prevent default close behavior

    // Add a WindowListener to detect when the user tries to close the window
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        // If an image is loaded, ask the user if they want to save changes
        if (!listener.quit()) {
          showExitConfirmation();
        }
      }
    });

    // Create and set the custom menu bar
    setJMenuBar(new MenuBar(this));

    setLayout(new BorderLayout());
    imagePreview = new ImageViewer();
    add(new JScrollPane(imagePreview), BorderLayout.CENTER); // Main image in the center
    add(histogramPreview = new ImageViewer(), BorderLayout.EAST); // Histogram on the right
    setVisible(true);
  }


  @Override
  public void updateImage(BufferedImage image) {
    SwingUtilities.invokeLater(() -> {
      imagePreview.updateImage(image); // Assuming setImage is a method in ImageViewer
    });
  }

  @Override
  public void updateHistogram(BufferedImage histogram) {
    SwingUtilities.invokeLater(() -> {
      histogramPreview.updateImage(histogram); // Assuming setImage is a method in ImageViewer
    });
  }

  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void enableAllFeatures() {
    for (JMenuItem item : disabledByDefault) {
      item.setEnabled(true);
    }
  }

  @Override
  public void showExitConfirmation() {
    int
        result =
        JOptionPane.showConfirmDialog(DefaultFrame.this,
                                      "Do you want to save changes before closing?",
                                      "Confirm",
                                      JOptionPane.YES_NO_CANCEL_OPTION);

    if (result == JOptionPane.YES_OPTION) {

      FileChooser fc = new FileChooser();
      int res = fc.showSaveDialog(this);

      if (res == JFileChooser.CANCEL_OPTION) {
        return;
      }

      listener.saveImage((res == JFileChooser.APPROVE_OPTION) ? fc.getSelectedFile() : null);
    }
    else if (result == JOptionPane.CANCEL_OPTION) {
      // If CANCEL_OPTION is chosen, do nothing (don't close the window)
      return;
    }
    dispose();
  }

  @Override
  public void addToDisabledByDefault(JMenuItem item) {
    disabledByDefault.add(item);
    item.setEnabled(false);
  }

  @Override
  public ViewComponentListener getViewComponentListener() {
    return listener;
  }

  @Override
  public JFrame getDisplayFrame() {
    return this;
  }


}
