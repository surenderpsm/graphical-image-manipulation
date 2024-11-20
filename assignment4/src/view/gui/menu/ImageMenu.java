package view.gui.menu;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import view.gui.SubComponentBinder;

public class ImageMenu extends JMenu {

  SubComponentBinder binder;

  public ImageMenu(SubComponentBinder binder) {
    super("Image");
    this.binder = binder;

    JMenu transform = new JMenu("Transform");

    // Add transform options
    JMenuItem hFlip = new JMenuItem("Horizontal Flip");
    binder.addToDisabledByDefault(hFlip);
    hFlip.addActionListener(e -> {
      binder.getViewComponentListener().hFlip();
    });

    JMenuItem vFlip = new JMenuItem("Vertical Flip");
    binder.addToDisabledByDefault(vFlip);
    vFlip.addActionListener(e -> {
      binder.getViewComponentListener().vFlip();
    });

    transform.add(hFlip);
    transform.add(vFlip);

    JMenuItem compress = new JMenuItem("Compress");
    binder.addToDisabledByDefault(compress);
    compress.addActionListener(e -> {
      // Create a dialog with the slider
      JDialog dialog = new JDialog(binder.getDisplayFrame(), "Adjust Value", true);
      dialog.setSize(350, 200);
      dialog.setLayout(null); // Absolute layout for simplicity
      dialog.setLocationRelativeTo(binder.getDisplayFrame()); // Center dialog on the frame

      // Create Slider
      JSlider slider = new JSlider(1, 99, 80); // Min: 0, Max: 100, Default: 50
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
      slider.addChangeListener(p -> valueLabel.setText("Ratio: " + slider.getValue() + "%"));

      // OK Button to confirm
      JButton okButton = new JButton("OK");
      okButton.setBounds(200, 120, 80, 30);
      dialog.add(okButton);

      // Add ActionListener to OK button
      okButton.addActionListener(okEvent -> {
        int finalValue = slider.getValue();
        binder.getViewComponentListener().compress(finalValue);
        dialog.dispose(); // Close the dialog
      });

      // Show Dialog
      dialog.setVisible(true);
    });

    // Add Transform submenu
    add(transform);
    // Add Compress item
    add(compress);
  }
}

