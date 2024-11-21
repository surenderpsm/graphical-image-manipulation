package view.gui;

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
import view.gui.menu.MenuBar;
import view.gui.utlis.FileChooser;

public class DefaultFrame extends JFrame implements UpdateObserver, SubComponentBinder {

  ImageViewer imagePreview;
  ImageViewer histogramPreview;
  ViewComponentListener listener;
  Set<JMenuItem> disabledByDefault = new HashSet<JMenuItem>();
  JButton confirmButton = new JButton("Confirm");
  JButton cancelButton = new JButton("Cancel");
  JSlider splitSlider = new JSlider(0, 100, 50);
  JPanel channelsPanel;

  public DefaultFrame(ViewComponentListener listener) {
    this.listener = listener;
    setTitle("GIME - Graphical Image Manipulation Enhancement");
    setSize(800, 600);
    setResizable(false);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Prevent default close behavior

    // Add a WindowListener to detect when the user tries to close the window
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        // If an image is loaded, ask the user if they want to save changes
        if (!listener.quit()) {
          showExitConfirmation();
        }
        else {
          dispose();
        }
      }
    });

    // Create and set the custom menu bar
    setJMenuBar(new MenuBar(this));

    // Split the layout between the main image and the right panel
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerLocation(500); // Fix the right panel to 1/4th the width (200px)
    splitPane.setResizeWeight(0.75);   // Allocate 75% of the space to the left panel
    splitPane.setOneTouchExpandable(false);

    // Left panel with slider and main image preview
    splitPane.setLeftComponent(createLeftPanel());

    // Right panel with histogram and channels
    splitPane.setRightComponent(createRightPanel());

    getContentPane().add(splitPane, BorderLayout.CENTER);
    setVisible(true);
  }

  private JPanel createLeftPanel() {
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.setBorder(new TitledBorder("Split Preview")); // Add the title

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

//    cancelButton.setEnabled(false);
    cancelButton.addActionListener(e -> {
      listener.setPreviewMode(true);
      listener.setConfirmation(false);
    });

    // Panel to hold the buttons
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


  private JPanel createRightPanel() {
    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new BorderLayout());

    // Histogram section
    JPanel histogramPanel = new JPanel(new BorderLayout());
    histogramPanel.setBorder(new TitledBorder("Histogram"));

    histogramPreview = new ImageViewer();
    histogramPanel.add(new JScrollPane(histogramPreview), BorderLayout.CENTER);

    // Channels section
    channelsPanel = new JPanel(new GridBagLayout());
    channelsPanel.setBorder(new TitledBorder("Channels"));

    // Define GridBagLayout constraints
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL; // Make buttons fill horizontally

    // Buttons: Red, Green, Blue
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

    // Add sections to the right panel
    rightPanel.add(histogramPanel, BorderLayout.CENTER);
    rightPanel.add(channelsPanel, BorderLayout.SOUTH);

    // Fix the width of the right panel to 1/4th of the frame size (200px)
    rightPanel.setPreferredSize(new Dimension(200, getHeight()));

    return rightPanel;
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
  public void setPreview(boolean enable) {
    confirmButton.setEnabled(enable);
    cancelButton.setEnabled(enable);
    splitSlider.setEnabled(enable);
  }

  @Override
  public void setChannelSettings(boolean enable) {
    for (Component component : channelsPanel.getComponents()) {
      component.setEnabled(enable);
    }
  }

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
