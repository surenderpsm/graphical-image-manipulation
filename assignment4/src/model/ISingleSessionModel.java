package model;

/**
 * The {@link controller.viewhandler.GUIHandler} has an instance of this implementation. Any changes
 * in the state, are updated directly to the view.
 * <p>
 * Implementations of this interface keep track of the.
 * <br>
 * A Single session model composes a model and manages the session. A session allows only working
 * on one image. A session also supports a preview mode. A preview mode doesn't update the actual
 * image. It works on a copy. Once the user confirms, the copy is saved as the current image.
 */
public interface ISingleSessionModel extends ModelReceiver {
  void updateView(boolean preview);
}
