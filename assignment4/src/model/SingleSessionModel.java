package model;

import controller.viewhandler.ViewUpdater;
import utils.arguments.ArgumentWrapper;
import utils.arguments.StringArgument;

/**
 * This class manages the GUI's state. A main alias is used to represent the image in the display
 * area.
 */
public class SingleSessionModel implements ISingleSessionModel {

  private IModel model;
  private final ViewUpdater view;
  private final String mainAlias;
  private final String previewAlias;
  private static final String MAIN_HISTOGRAM_ALIAS = "mainHistogram";

  /**
   * @param sharer    The modelSharer is used to receive the model.
   * @param view      A ViewUpdater is analogous to an observer.
   * @param mainAlias The alias for the image being referred to
   */
  public SingleSessionModel(ModelSharer sharer,
                            ViewUpdater view,
                            String mainAlias,
                            String previewAlias) {
    sharer.shareWith(this);
    this.view = view;
    this.mainAlias = mainAlias;
    this.previewAlias = previewAlias;
    System.out.println("SingleSessionModel.SingleSessionModel");
  }

  @Override
  public void updateView(boolean preview) {
    if (model == null) {
      throw new IllegalStateException("Model is not set");
    }

    String alias = (preview) ? previewAlias : mainAlias;
    view.updateDisplay(model.getImage(alias));
    model.execute("histogram",
                  new ArgumentWrapper(new StringArgument(alias),
                                      new StringArgument(MAIN_HISTOGRAM_ALIAS)));
    view.updateHistogram(model.getHistogram(MAIN_HISTOGRAM_ALIAS));
  }


  @Override
  public void share(IModel model) {
    setModel(model);
  }

  private void setModel(IModel model) {
    this.model = model;
  }
}
