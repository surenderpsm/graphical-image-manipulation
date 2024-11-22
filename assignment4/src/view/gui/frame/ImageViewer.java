package view.gui.frame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A JPanel that displays an image with scrolling capability.
 * <p>
 * This class provides a wrapper for displaying an image inside a scrollable area. It updates the
 * displayed image when requested and handles the rendering of the image.
 */
public class ImageViewer extends JPanel {

  private final ImagePanel imagePanel; // Keep a reference to the inner ImagePanel
  private final JScrollPane scrollPane;

  /**
   * Constructs a new ImageViewer with no initial image. Initializes the internal components and
   * sets the layout.
   */
  public ImageViewer() {
    imagePanel = new ImagePanel(null); // Start with no image
    scrollPane = new JScrollPane(imagePanel);
    setLayout(new java.awt.BorderLayout()); // Use BorderLayout for better scrollPane positioning
    add(scrollPane, java.awt.BorderLayout.CENTER);
  }

  /**
   * Updates the displayed image.
   *
   * @param newImage the new {@link BufferedImage} to display.
   */
  public void updateImage(BufferedImage newImage) {
    imagePanel.setImage(newImage); // Update the image
    imagePanel.revalidate();      // Update layout if the size changes
    imagePanel.repaint();         // Trigger a repaint
  }

  /**
   * Inner class representing a panel to draw and display the image. This class is used within the
   * ImageViewer to actually render the image.
   */
  static class ImagePanel extends JPanel {

    private BufferedImage image;

    /**
     * Constructs an ImagePanel with the specified image.
     *
     * @param image the initial image to display.
     */
    public ImagePanel(BufferedImage image) {
      setImage(image);
    }

    /**
     * Sets the image to be displayed by this panel.
     *
     * @param image the image to display.
     */
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

    /**
     * Paints the image on the panel.
     *
     * @param g the {@link Graphics} object to paint the image.
     */
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
