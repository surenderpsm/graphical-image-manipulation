package model;

/**
 * By using a sharer and a receiver, we can prevent the coordinator to get access to the object. The
 * calling object passes a {@link ModelReceiver} to it, which is used by the {@code ModelSharer} to
 * share the model with the {@code ModelReceiver}.
 * <br>
 * In the {@link controller.viewhandler.GUIHandler}, the {@link controller.IControllerView} being a
 * ModelSharer can share a model to a {@link ISingleSessionModel} which is a ModelReceiver. The
 * middle man, i.e.: The GUIHandler has no access to the model.
 */
public interface ModelSharer {

  void shareWith(ModelReceiver receiver);
}
