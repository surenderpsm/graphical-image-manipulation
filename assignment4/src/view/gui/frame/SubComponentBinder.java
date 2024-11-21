package view.gui.frame;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import view.gui.ComponentObserver;

public interface SubComponentBinder {

  void addToDisabledByDefault(JMenuItem item);

  ComponentObserver getViewComponentListener();

  JFrame getDisplayFrame();
}
