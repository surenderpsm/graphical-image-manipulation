package model;

import controller.viewhandler.GUIAdapter;

/**
 * The {@link GUIAdapter} has an instance of this implementation. Any changes in the state, are
 * updated directly to the view.
 * <br>
 * A Single session model composes a model and manages the session. A session allows only working on
 * one image. A session also supports a preview mode. A preview mode doesn't update the actual
 * image. It works on a copy. Once the user confirms, the copy is saved as the current image.
 */
public interface ISingleSessionModel extends ModelReceiver {

  /**
   * Update the view based on the preview. Updating currently involves updating imageviewer and
   * histogram.
   *
   * @param preview a boolean.
   */
  void updateView(boolean preview);
}
