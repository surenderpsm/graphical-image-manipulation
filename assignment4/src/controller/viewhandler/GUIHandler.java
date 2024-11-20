package controller.viewhandler;

import controller.IControllerView;
import java.io.File;
import model.ISingleSessionModel;
import model.SingleSessionModel;
import utils.BufferedImageGenerator;
import utils.HistogramGenerator;
import utils.arguments.ArgumentWrapper;
import utils.arguments.MandatedArgWrapper;
import utils.arguments.OptionalArgumentKeyword;
import utils.arguments.StringArgument;
import view.gui.GUI;
import view.gui.GUIHandlingObject;
import view.gui.GUIViewListener;

/**
 * The GUIHandler handles only one image at a time, hence we don't need any aliases when performing
 * commands. Aliasing is managed by this ViewHandler instead of the user.
 */
public class GUIHandler implements ViewHandler, GUIViewListener, ViewUpdater {

  private static final int MAX_VERSIONS = 10;
  private static final String MAIN_ALIAS = "image";
  private static final String PREVIEW_ALIAS = "preview";
  private boolean unsavedChanges = false;
  private boolean loaded = false;
  GUIHandlingObject gui;
  ISingleSessionModel modelView;
  IControllerView controller;

  public GUIHandler(IControllerView controller) {
    gui = new GUI(this);
    this.controller = controller;
    modelView = new SingleSessionModel(controller, this, MAIN_ALIAS, PREVIEW_ALIAS);
  }

  @Override
  public void notifyExecutionOnSuccess() {
    if (!loaded) {
      gui.enableAllFeatures();
      loaded = true;
    }
    modelView.updateView(gui.isPreviewMode());
  }

  @Override
  public void notifyExecutionOnFailure(String reason) {
    gui.displayError(reason);
  }

  @Override
  public void listenForInput() {

  }


  @Override
  public boolean requestApplicationExit() {
    // check for current state, if there is some image loaded. then ask the user if
    return !unsavedChanges;
  }

  @Override
  public void onLoadImage(File file) {
    // @todo set up alias management
    try {
      controller.loadImage(file, MAIN_ALIAS);
    } catch (Exception e) {
      notifyExecutionOnFailure(e.getMessage());
    }
    // @todo update display
  }

  @Override
  public void onSaveImage(File file) {
    try {
      controller.saveImage(file, MAIN_ALIAS);
      unsavedChanges = false;
    } catch (Exception e) {
      notifyExecutionOnFailure(e.getMessage());
    }
    //@todo allow quit without confirmation.
  }

  @Override
  public void onCommandExecuted(String command, ArgumentWrapper args) {
    MandatedArgWrapper wrapper = controller.getMandatedArgs(command);
    // check if preview mode is enabled.
    boolean preview = gui.isPreviewMode();
    boolean confirm = gui.isConfirmed();
    try {
      // @todo the aliases are not added to the command.
      // save to preview alias if preview mode is enabled.
      if(!confirm) {
        modelView.updateView(false);
        return;
      }
      String alias = (preview) ? PREVIEW_ALIAS : MAIN_ALIAS;
      // get split from the gui if preview mode is enabled.
      int split = (preview) ? gui.getSplit() : 100;
      args.setArguments(new StringArgument(MAIN_ALIAS), new StringArgument(alias));
      wrapper.setArgument(OptionalArgumentKeyword.SPLIT, split);
      wrapper.mandate(args);
      controller.invokeCommand(command, wrapper);
      unsavedChanges = true;
    } catch (Exception e) {
      notifyExecutionOnFailure(e.getMessage());
    }
  }


  @Override
  public void updateDisplay(int[][][] image) {
    gui.updateImage(BufferedImageGenerator.createBufferedImage(image));
  }

  @Override
  public void updateHistogram(int[][] histogram) {
    int[][][] image = new HistogramGenerator(histogram).getImage();
    gui.updateHistogram(BufferedImageGenerator.createBufferedImage(image));
  }

}
