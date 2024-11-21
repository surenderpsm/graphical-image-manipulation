package view.gui.frame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImageViewer extends JPanel {

  private final ImagePanel imagePanel; // Keep a reference to the inner ImagePanel
  private final JScrollPane scrollPane;

  public ImageViewer() {
    imagePanel = new ImagePanel(null); // Start with no image
    scrollPane = new JScrollPane(imagePanel);
    setLayout(new java.awt.BorderLayout()); // Use BorderLayout for better scrollPane positioning
    add(scrollPane, java.awt.BorderLayout.CENTER);
  }

  /**
   * Updates the displayed image.
   *
   * @param newImage the new BufferedImage to display
   */
  public void updateImage(BufferedImage newImage) {
    imagePanel.setImage(newImage); // Update the image
    imagePanel.revalidate();      // Update layout if the size changes
    imagePanel.repaint();         // Trigger a repaint
  }

  // Custom panel to draw the image
  static class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(BufferedImage image) {
      setImage(image);
    }

    public void setImage(BufferedImage image) {
      this.image = image;
      if (image != null) {
        // Set the preferred size to match the new image dimensions
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
      }
      else {
        // Handle null image gracefully
        setPreferredSize(new Dimension(0, 0));
      }
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (image != null) {
        // Draw the image at (0, 0) in its full size
        g.drawImage(image, 0, 0, this);
      }
    }
  }
}
