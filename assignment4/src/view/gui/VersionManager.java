package view.gui;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * This is used to add versions of image to a stack like structure. The previous {@code maxSize}
 * edits on the image can be restored. Stores the images as {@link BufferedImage}.
 * <h3>
 * Limitations:
 * </h3>
 * This doesn't support a redo option currently. Operations when undone, are lost permanently.
 */
public class VersionManager {

  private final LinkedList<BufferedImage> history = new LinkedList<>();
  private final int maxSize;

  /**
   * Constructs a new VersionManager.
   *
   * @param maxSize The maximum size of the stack.
   */
  public VersionManager(int maxSize) {
    this.maxSize = maxSize;
  }

  /**
   * Add a new version to the stack. Also, remove the oldest version, if the max versions are
   * reached.
   *
   * @param image a BufferedImage to add to the stack.
   */
  public void update(BufferedImage image) {
    if (history.size() >= maxSize) {
      // remove oldest version.
      history.removeFirst();
    }
    history.add(image);
  }

  /**
   * Get the latest previous change. Basically, a peek operation in stack terms.
   *
   * @return a BufferedImage.
   */
  public BufferedImage getPrevious() {
    if (history.isEmpty()) {
      throw new IllegalStateException("No more previous versions");
    }
    return history.getLast();
  }

  /**
   * Pops the latest operation.
   *
   * @return a BufferedImage.
   */
  public BufferedImage undo() {
    if (history.isEmpty()) {
      throw new IllegalStateException("No more previous versions");
    }
    return history.removeLast();
  }

  /**
   * Checks if history is empty.
   *
   * @return false if not empty else true.
   */
  public boolean isEmpty() {
    return history.isEmpty();
  }

  /**
   * The current size of the stack.
   *
   * @return integer size.
   */
  public int size() {
    return history.size();
  }

}