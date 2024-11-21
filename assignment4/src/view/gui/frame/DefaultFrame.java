package view.gui.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import view.gui.ComponentObserver;
import view.gui.frame.menu.MenuBar;

/**
 * Represents the main GUI frame of the application for image manipulation. This frame includes
 * features like image preview, histogram display, split preview functionality, and adjustable
 * channel settings.
 * <br>
 * The frame observes updates from the {@link ComponentObserver} and allows the user to interact
 * with image manipulation commands.
 */
public class DefaultFrame extends JFrame implements UpdateObserver, SubComponentBinder {

  private ImageViewer imagePreview;
  private ImageViewer histogramPreview;
  private final ComponentObserver listener;
  private final Set<JMenuItem> disabledByDefault = new HashSet<>();
  private final JButton confirmButton = new JButton("Confirm");
  private final JButton cancelButton = new JButton("Cancel");
  private final JSlider splitSlider = new JSlider(0, 100, 50);
  private JPanel channelsPanel;

  /**
   * Constructs a new DefaultFrame with the provided ComponentObserver. Initializes all UI
   * components and sets up the layout.
   *
   * @param listener the observer to handle user actions and commands.
   */
  public DefaultFrame(ComponentObserver listener) {
    this.listener = listener;
    setTitle("GIME - Graphical Image Manipulation Enhancement");
    setSize(800, 600);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Prevent default close behavior

    // Add a WindowListener to detect when the user tries to close the window
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        if (!listener.quit()) {
          showExitConfirmation();
        }
        else {
          dispose();
        }
      }
    });

    // Set the menu bar
    setJMenuBar(new MenuBar(this));

    // Create and configure split-pane layout
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerLocation(500); // Fix the right panel to 1/4th the width (200px)
    splitPane.setResizeWeight(0.75);   // Allocate 75% of the space to the left panel
    splitPane.setOneTouchExpandable(false);

    // Left panel with slider and main image preview
    splitPane.setLeftComponent(createLeftPanel());
    splitPane.setRightComponent(createRightPanel());
    getContentPane().add(splitPane, BorderLayout.CENTER);
    setVisible(true);
  }

  /**
   * Creates the left panel of the frame, which includes the image preview, split slider, and action
   * buttons (Confirm and Cancel).
   *
   * @return the constructed left panel.
   */
  private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.setBorder(new TitledBorder("Split Preview"));

    // Create the slider
    splitSlider.setMajorTickSpacing(20); // Major ticks every 20
    splitSlider.setPaintTicks(true); // Show tick marks
    splitSlider.setPaintLabels(true); // Show labels

    // Add listener to the slider (example: print value when adjusted)
    splitSlider.addChangeListener(e -> {
      int value = splitSlider.getValue();
      listener.splitLevel(value); // Notify listener about slider changes
    });

    // Create the image preview area
    imagePreview = new ImageViewer();
    JScrollPane imageScrollPane = new JScrollPane(imagePreview);

    // Create the "Confirm" button
    confirmButton.setPreferredSize(new Dimension(0, 40)); // Set fixed height for the button
    confirmButton.addActionListener(e -> {
      listener.setPreviewMode(false);
      listener.setConfirmation(true);
    });

    cancelButton.addActionListener(e -> {
      listener.setPreviewMode(false);
      listener.setConfirmation(false);
    });

    JPanel buttonPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons fill horizontally
    gbc.insets = new Insets(5, 5, 5, 5); // Add padding around buttons

    // Add the Saving Confirm button
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Full width
    buttonPanel.add(confirmButton, gbc);

    // Add the Cancel button
    gbc.gridy = 1; // Next row
    buttonPanel.add(cancelButton, gbc);

    // Add components to the panel
    leftPanel.add(splitSlider, BorderLayout.NORTH); // Add slider at the top
    leftPanel.add(imageScrollPane, BorderLayout.CENTER); // Add image preview in the center
    leftPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button at the bottom

    return leftPanel;
  }

  /**
   * Creates the right panel of the frame, which includes the histogram display and channel
   * adjustment controls.
   *
   * @return the constructed right panel.
   */
  private JPanel createRightPanel() {
    JPanel rightPanel = new JPanel(new BorderLayout());
    JPanel histogramPanel = new JPanel(new BorderLayout());
    histogramPanel.setBorder(new TitledBorder("Histogram"));

    histogramPreview = new ImageViewer();
    histogramPanel.add(new JScrollPane(histogramPreview), BorderLayout.CENTER);

    // Channels section
    channelsPanel = new JPanel(new GridBagLayout());
    channelsPanel.setBorder(new TitledBorder("Channels"));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons fill horizontally

    JButton redButton = new JButton("Red");
    redButton.addActionListener(e -> {
      listener.resetComponents();
      listener.redComponent();
    });
    JButton greenButton = new JButton("Green");
    greenButton.addActionListener(e -> {
      listener.resetComponents();
      listener.greenComponent();
    });
    JButton blueButton = new JButton("Blue");
    blueButton.addActionListener(e -> {
      listener.resetComponents();
      listener.blueComponent();
    });
    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener(e -> {
      listener.resetComponents();
    });
    // Add buttons to the GridBagLayout
    gbc.gridx = 0; // First column
    gbc.gridy = 0; // Row 0
    gbc.weightx = 1.0;
    gbc.insets = new Insets(5, 5, 5, 5); // Add some padding
    channelsPanel.add(redButton, gbc);

    gbc.gridy = 1; // Row 1
    channelsPanel.add(greenButton, gbc);

    gbc.gridy = 2; // Row 2
    channelsPanel.add(blueButton, gbc);

    // Add Reset button in the second column
    gbc.gridx = 1; // Second column
    gbc.gridy = 0; // Align with the top of the first column
    gbc.gridheight = 3; // Span three rows
    gbc.fill = GridBagConstraints.BOTH; // Fill horizontally and vertically
    channelsPanel.add(resetButton, gbc);

    rightPanel.add(histogramPanel, BorderLayout.CENTER);
    rightPanel.add(channelsPanel, BorderLayout.SOUTH);
    rightPanel.setPreferredSize(new Dimension(200, getHeight()));
    return rightPanel;
  }

  /**
   * Updates the image preview with the given image.
   *
   * @param image the new image to display.
   */
  @Override
  public void updateImage(BufferedImage image) {
    SwingUtilities.invokeLater(() -> imagePreview.updateImage(image));
  }

  /**
   * Updates the histogram preview with the given histogram image.
   *
   * @param histogram the new histogram to display.
   */
  @Override
  public void updateHistogram(BufferedImage histogram) {
    SwingUtilities.invokeLater(() -> histogramPreview.updateImage(histogram));
  }

  /**
   * Displays an error message to the user.
   *
   * @param error the error message to display.
   */
  @Override
  public void displayError(String error) {
    JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Enables all disabled features in the menu.
   */
  @Override
  public void enableAllFeatures() {
    for (JMenuItem item : disabledByDefault) {
      item.setEnabled(true);
    }
  }

  /**
   * Sets whether preview-related components are enabled or not.
   *
   * @param enable true to enable, false to disable.
   */
  @Override
  public void setPreview(boolean enable) {
    confirmButton.setEnabled(enable);
    cancelButton.setEnabled(enable);
    splitSlider.setEnabled(enable);
  }

  /**
   * Enables or disables the channel adjustment controls.
   *
   * @param enable true to enable, false to disable.
   */
  @Override
  public void setChannelSettings(boolean enable) {
    for (Component component : channelsPanel.getComponents()) {
      component.setEnabled(enable);
    }
  }

  /**
   * Displays an exit confirmation dialog, prompting the user to save changes.
   */
  private void showExitConfirmation() {
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

  /**
   * Adds a menu item to the set of features disabled by default.
   *
   * @param item the menu item to disable by default.
   */
  @Override
  public void addToDisabledByDefault(JMenuItem item) {
    disabledByDefault.add(item);
    item.setEnabled(false);
  }

  /**
   * Retrieves the ComponentObserver for handling user interactions.
   *
   * @return the associated ComponentObserver.
   */
  @Override
  public ComponentObserver getViewComponentListener() {
    return listener;
  }

  /**
   * Retrieves the current display frame.
   *
   * @return this frame instance.
   */
  @Override
  public JFrame getDisplayFrame() {
    return this;
  }
}
