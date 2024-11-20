package controller.viewhandler;

/**
 * A ISingleSessionModel can update the instance of this interface.
 */
public interface ViewUpdater {
  /**
   * Updates the image on the display area.
   *
   * @param image an int[][][] to update the image.
   */
  void updateDisplay(int[][][] image);
  /**
   * Updates the histogram whenever an operation is performed.
   *
   * @param histogram an int[][] of the histogram.
   */
  void updateHistogram(int[][] histogram);

}
