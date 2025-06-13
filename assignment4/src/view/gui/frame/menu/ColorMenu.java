package view.gui.frame.menu;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import view.gui.frame.SubComponentBinder;

/**
 * The ColorMenu class represents the "Color" menu in the GUI frame.
 * <p>
 * This menu provides options for color-related adjustments, including Levels Adjustment and Color
 * Correction, along with various color effects like Grayscale and Sepia. The menu integrates with a
 * listener that interacts with the underlying image processing logic.
 * </p>
 */
public class ColorMenu extends JMenu {

  SubComponentBinder binder;

  /**
   * Constructs the ColorMenu and adds menu items for color adjustments and effects.
   *
   * @param binder the {@link SubComponentBinder} that provides access to necessary components like
   *               listeners for color adjustments.
   */
  public ColorMenu(SubComponentBinder binder) {
    super("Color");
    this.binder = binder;
    JMenuItem levels = new JMenuItem("Levels Adjustment");
    binder.addToDisabledByDefault(levels);
    levels.addActionListener(e -> {
      // Create the dialog
      JDialog dialog = new JDialog(binder.getDisplayFrame(), "Levels Adjustment", true);
      dialog.setSize(400, 350);
      dialog.setLayout(null); // Absolute layout for simplicity
      dialog.setLocationRelativeTo(binder.getDisplayFrame()); // Center dialog on the frame

      // Labels for sliders
      JLabel blackPointLabel = new JLabel("Black Point: 0");
      blackPointLabel.setBounds(20, 20, 150, 20);
      dialog.add(blackPointLabel);

      JLabel midpointLabel = new JLabel("Midpoint: 128");
      midpointLabel.setBounds(20, 100, 150, 20);
      dialog.add(midpointLabel);

      JLabel whitePointLabel = new JLabel("White Point: 255");
      whitePointLabel.setBounds(20, 180, 150, 20);
      dialog.add(whitePointLabel);

      // Black Point Slider
      JSlider blackPointSlider = new JSlider(0, 255, 0);
      blackPointSlider.setBounds(20, 50, 350, 40);
      blackPointSlider.setMajorTickSpacing(50);
      blackPointSlider.setPaintTicks(true);
      blackPointSlider.setPaintLabels(true);
      dialog.add(blackPointSlider);

      // Midpoint Slider
      JSlider midpointSlider = new JSlider(0, 255, 128);
      midpointSlider.setBounds(20, 130, 350, 40);
      midpointSlider.setMajorTickSpacing(50);
      midpointSlider.setPaintTicks(true);
      midpointSlider.setPaintLabels(true);
      dialog.add(midpointSlider);

      // White Point Slider
      JSlider whitePointSlider = new JSlider(0, 255, 255);
      whitePointSlider.setBounds(20, 210, 350, 40);
      whitePointSlider.setMajorTickSpacing(50);
      whitePointSlider.setPaintTicks(true);
      whitePointSlider.setPaintLabels(true);
      dialog.add(whitePointSlider);

      // Flag to prevent recursive updates
      final boolean[] isAdjusting = {false};

      // Add listeners to enforce constraints
      blackPointSlider.addChangeListener(e1 -> {
        if (isAdjusting[0]) {
          return;
        }
        isAdjusting[0] = true;
        int black = blackPointSlider.getValue();
        if (black > midpointSlider.getValue()) {
          midpointSlider.setValue(black); // Adjust midpoint if black point exceeds it
        }
        blackPointLabel.setText("Black Point: " + black);
        isAdjusting[0] = false;
      });

      midpointSlider.addChangeListener(e1 -> {
        if (isAdjusting[0]) {
          return;
        }
        isAdjusting[0] = true;
        int mid = midpointSlider.getValue();
        if (mid < blackPointSlider.getValue()) {
          midpointSlider.setValue(blackPointSlider.getValue()); // Adjust midpoint if below black point
        }
        else if (mid > whitePointSlider.getValue()) {
          midpointSlider.setValue(whitePointSlider.getValue()); // Adjust midpoint if above white point
        }
        midpointLabel.setText("Midpoint: " + mid);
        isAdjusting[0] = false;
      });

      whitePointSlider.addChangeListener(e1 -> {
        if (isAdjusting[0]) {
          return;
        }
        isAdjusting[0] = true;
        int white = whitePointSlider.getValue();
        if (white < midpointSlider.getValue()) {
          midpointSlider.setValue(white); // Adjust midpoint if white point is below it
        }
        whitePointLabel.setText("White Point: " + white);
        isAdjusting[0] = false;
      });

      // OK Button
      JButton okButton = new JButton("OK");
      okButton.setBounds(150, 280, 80, 30);
      dialog.add(okButton);

      // ActionListener for OK Button
      okButton.addActionListener(okEvent -> {
        int blackPoint = blackPointSlider.getValue();
        int midpoint = midpointSlider.getValue();
        int whitePoint = whitePointSlider.getValue();

        binder.getViewComponentListener().adjustLevels(blackPoint, midpoint, whitePoint);

        dialog.dispose(); // Close the dialog
      });

      // Show the dialog
      dialog.setVisible(true);
    });

    JMenuItem colorCorrection = new JMenuItem("Color Correction");
    binder.addToDisabledByDefault(colorCorrection);
    colorCorrection.addActionListener(e -> {
      binder.getViewComponentListener().colorCorrect();
    });

    // Add Effects submenu
    add(new EffectsSubMenu(binder));

    // Add other color options
    add(levels);
    add(colorCorrection);
  }

  /**
   * The EffectsSubMenu class provides additional color effects like Grayscale and Sepia. These
   * options can be applied to the image.
   */
  private static class EffectsSubMenu extends JMenu {

    /**
     * Constructs the Effects submenu and adds color effects options.
     *
     * @param binder the {@link SubComponentBinder} that provides access to necessary components.
     */
    public EffectsSubMenu(SubComponentBinder binder) {
      super("Effects");
      binder.addToDisabledByDefault(this);

      JMenuItem grayscale = new JMenuItem("Grayscale");
      grayscale.addActionListener(e -> {
        binder.getViewComponentListener().grayscale();
      });

      JMenuItem sepia = new JMenuItem("Sepia");
      sepia.addActionListener(e -> {
        binder.getViewComponentListener().sepia();
      });

      // Add color effects
      add(grayscale);
      add(sepia);
    }
  }
}
