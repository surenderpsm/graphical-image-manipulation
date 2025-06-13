package view.gui.frame.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import view.gui.frame.SubComponentBinder;

/**
 * ImageMenu is a custom menu component that provides various image transformation and manipulation
 * options, including horizontal and vertical flips, downscaling, and compression. It extends
 * {@link JMenu} and binds actions to the view component listener through the
 * {@link SubComponentBinder}.
 */
public class ImageMenu extends JMenu {

  /**
   * Constructs a new ImageMenu with options for image transformations and manipulations. The
   * available options include horizontal and vertical flips, downscaling, and compression. These
   * actions are bound to the provided {@link SubComponentBinder} to manage their availability and
   * functionality.
   *
   * @param binder the {@link SubComponentBinder} responsible for managing the menu items and
   *               binding actions to the view component listener
   */
  public ImageMenu(SubComponentBinder binder) {
    super("Image");

    // Transform menu
    JMenu transform = new JMenu("Transform");
    binder.addToDisabledByDefault(transform);

    // Add horizontal and vertical flip options
    JMenuItem hFlip = new JMenuItem("Horizontal Flip");
    hFlip.addActionListener(e -> {
      binder.getViewComponentListener().hFlip();
    });

    JMenuItem vFlip = new JMenuItem("Vertical Flip");
    vFlip.addActionListener(e -> {
      binder.getViewComponentListener().vFlip();
    });

    transform.add(hFlip);
    transform.add(vFlip);

    // Downscale option
    JMenuItem downscale = new JMenuItem("Downscale");
    binder.addToDisabledByDefault(downscale);
    downscale.addActionListener(e -> {
      // Create a modal dialog for entering new image dimensions
      JDialog dialog = new JDialog(binder.getDisplayFrame(), "Set New Dimensions", true);
      dialog.setLayout(new BorderLayout(10, 10)); // BorderLayout for better spacing

      // Create a panel for input fields
      JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
      JLabel label1 = new JLabel("Width:");
      JTextField widthField = new JTextField();
      JLabel label2 = new JLabel("Height:");
      JTextField heightField = new JTextField();

      // Add input components to the panel
      inputPanel.add(label1);
      inputPanel.add(widthField);
      inputPanel.add(label2);
      inputPanel.add(heightField);

      // Create a panel for the button
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      JButton okButton = new JButton("OK");
      okButton.addActionListener(f -> {
        try {
          int width = Integer.parseInt(widthField.getText());
          int height = Integer.parseInt(heightField.getText());
          binder.getViewComponentListener().downscale(width, height);
          dialog.dispose(); // Close the dialog
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(binder.getDisplayFrame(),
                                        "Please enter valid numbers!",
                                        "Input Error",
                                        JOptionPane.ERROR_MESSAGE);
        }
      });
      buttonPanel.add(okButton);

      // Add panels to the dialog
      dialog.add(inputPanel, BorderLayout.CENTER);
      dialog.add(buttonPanel, BorderLayout.SOUTH);

      // Set dialog properties
      dialog.setSize(300, 150); // Set fixed size for neat appearance
      dialog.setLocationRelativeTo(binder.getDisplayFrame()); // Center on the parent frame
      dialog.setVisible(true);
    });

    // Compression option
    JMenuItem compress = new JMenuItem("Compress");
    binder.addToDisabledByDefault(compress);
    compress.addActionListener(e -> {
      // Create a dialog with a slider to adjust compression ratio
      JDialog dialog = new JDialog(binder.getDisplayFrame(), "Adjust Value", true);
      dialog.setSize(350, 200);
      dialog.setLayout(null); // Absolute layout for simplicity
      dialog.setLocationRelativeTo(binder.getDisplayFrame()); // Center dialog on the frame

      // Create Slider for compression ratio
      JSlider slider = new JSlider(0, 100, 80); // Min: 0, Max: 100, Default: 80
      slider.setBounds(20, 20, 250, 50);
      slider.setMajorTickSpacing(10); // Ticks every 10
      slider.setPaintTicks(true); // Display tick marks
      slider.setPaintLabels(true); // Display tick labels
      dialog.add(slider);

      // Label to show current value
      JLabel valueLabel = new JLabel("Ratio: " + slider.getValue() + "%");
      valueLabel.setBounds(20, 80, 100, 20);
      dialog.add(valueLabel);

      // Add ChangeListener to Slider to update label dynamically
      int sliderValue = slider.getValue();
      if (slider.getValue() == 100) {
        sliderValue = 99;
      }
      else if (slider.getValue() == 0) {
        sliderValue = 1;
      }
      int finalSliderValue = sliderValue;
      slider.addChangeListener(p -> valueLabel.setText("Ratio: " + finalSliderValue + "%"));

      // OK Button to confirm compression
      JButton okButton = new JButton("OK");
      okButton.setBounds(200, 120, 80, 30);
      dialog.add(okButton);

      // Add ActionListener to OK button
      okButton.addActionListener(okEvent -> {
        binder.getViewComponentListener().compress(finalSliderValue);
        dialog.dispose(); // Close the dialog
      });

      // Show the dialog
      dialog.setVisible(true);
    });

    // Add Transform submenu, Compress, and Downscale items to the menu
    add(transform);
    add(compress);
    add(downscale);
  }
}
