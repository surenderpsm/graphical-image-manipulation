package view.menu;


import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ColorMenu extends JMenu {

  public ColorMenu() {
    super("Color");

    // Add Effects submenu
    add(new EffectsSubMenu());

    // Add other color options
    add(new JMenuItem("Levels"));
    add(new JMenuItem("Color Correct"));
  }

  private static class EffectsSubMenu extends JMenu {

    public EffectsSubMenu() {
      super("Effects");

      // Add color effects
      add(new JMenuItem("Grayscale"));
      add(new JMenuItem("Sepia"));
    }
  }
}
