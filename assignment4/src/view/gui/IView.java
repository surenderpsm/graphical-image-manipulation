package view.gui;

import java.awt.image.BufferedImage;

/**
 * This interface describes the different methods that can be performed by the view. The Controller
 * can access these methods to manipulate the view.
 */
public interface IView {

  /**
   * Display the provided image in the viewing area. The image will not be scaled down if it exceeds
   * the viewing area. If it overflows the viewing area, it will be scrollable.
   *
   * @param image The BufferedImage object to set to the viewing area.
   */
  void displayImage(BufferedImage image);


}
