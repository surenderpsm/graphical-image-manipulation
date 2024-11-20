package view.gui;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

public interface SubComponentBinder {
  void addToDisabledByDefault(JMenuItem item);
  ViewComponentListener getViewComponentListener();
  JFrame getDisplayFrame();
}
