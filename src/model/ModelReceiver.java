package model;

import controller.Features;
import controller.viewhandler.GUIAdapter;

/**
 * By using a sharer and a receiver, we can prevent the coordinator to get access to the object. The
 * calling object passes a {@link ModelReceiver} to it, which is used by the {@code ModelSharer} to
 * share the model with the {@code ModelReceiver}.
 * <br>
 * In the {@link GUIAdapter}, the {@link Features} being a ModelSharer can
 * share a model to a {@link ISingleSessionModel} which is a ModelReceiver. The middle man, i.e.:
 * The GUIAdapter has no access to the model.
 */
public interface ModelReceiver {

  void share(IModel model);
}
