package controller.viewhandler;

import controller.Features;
import java.io.File;
import model.ISingleSessionModel;
import model.SingleSessionModel;
import utils.BufferedImageGenerator;
import utils.HistogramGenerator;
import utils.arguments.ArgumentWrapper;
import utils.arguments.MandatedArgWrapper;
import utils.arguments.OptionalArgumentKeyword;
import utils.arguments.StringArgument;
import view.gui.GUIImpl;
import view.gui.GUI;
import view.gui.GUIObserver;

/**
 * The GUIAdapter connects the GUI to the controller, allowing the controller to
 * interact with the user interface and manage a single image at a time.
 * <p>
 * This adapter handles image loading, saving, and command execution in the GUI,
 * along with managing a preview mode. The adapter ensures that no aliases
 * are required for the user when executing commands, as aliasing is handled
 * internally by the adapter. It also manages unsaved changes and handles
 * confirmation for preview mode operations.
 * </p>
 */
public class GUIAdapter implements ViewAdapter, GUIObserver, ViewUpdater {

  private static final String MAIN_ALIAS = "image"; // Main alias for the loaded image
  private static final String PREVIEW_ALIAS = "preview"; // Alias for the preview image
  private boolean unsavedChanges = false; // Tracks whether there are unsaved changes
  private boolean loaded = false; // Indicates if an image has been loaded
  private final GUI gui; // The GUI instance
  private ISingleSessionModel modelView; // Model view that manages the image state
  private Features controller; // The controller responsible for handling commands

  // Constructors to initialize the GUI and add the observer
  public GUIAdapter() {
    gui = new GUIImpl();
    gui.addObserver(this);
  }

  public GUIAdapter(GUI gui) {
    this.gui = gui;
    gui.addObserver(this);
  }

  // Adds the controller to the adapter
  @Override
  public void addController(Features controller) {
    this.controller = controller;
    modelView = new SingleSessionModel(controller, this, MAIN_ALIAS, PREVIEW_ALIAS);
  }

  // Notifies the view when a command is successfully executed
  @Override
  public void notifyExecutionOnSuccess() {
    if (!loaded) {
      gui.enableAllFeatures();
      loaded = true;
    }
    modelView.updateView(gui.isPreviewMode());
  }

  // Notifies the view of a command failure
  @Override
  public void notifyExecutionOnFailure(String reason) {
    gui.displayError(reason);
  }

  // Listens for user input (implemented in the specific adapters)
  @Override
  public void listenForInput() {
    // No direct CLI input handling in this adapter
  }

  // Requests application exit, ensuring there are no unsaved changes
  @Override
  public boolean requestApplicationExit() {
    return !unsavedChanges; // Prevent exit if there are unsaved changes
  }

  // Handles image loading and updates the model
  @Override
  public void onLoadImage(File file) {
    try {
      controller.loadImage(file, MAIN_ALIAS);
    } catch (Exception e) {
      notifyExecutionOnFailure(e.getMessage());
    }
  }

  // Handles saving the current image
  @Override
  public void onSaveImage(File file) {
    try {
      controller.saveImage(file, MAIN_ALIAS);
      unsavedChanges = false;
    } catch (Exception e) {
      notifyExecutionOnFailure(e.getMessage());
    }
  }

  // Executes the command with the given arguments, updating the view based on preview mode
  @Override
  public void onCommandExecuted(String command, ArgumentWrapper args) {
    MandatedArgWrapper wrapper = controller.getMandatedArgs(command);
    boolean preview = gui.isPreviewMode();
    boolean confirm = gui.isConfirmed();
    try {
      // Abort execution if not confirmed in preview mode
      if (!confirm) {
        modelView.updateView(false); // Update view without changes
        return;
      }

      // Set alias based on preview mode
      String alias = (preview) ? PREVIEW_ALIAS : MAIN_ALIAS;
      int split = (preview) ? gui.getSplit() : 100;

      // Set arguments and pass to the controller
      args.setArguments(new StringArgument(MAIN_ALIAS), new StringArgument(alias));
      wrapper.setArgument(OptionalArgumentKeyword.SPLIT, split);
      wrapper.mandate(args);
      controller.invokeCommand(command, wrapper);

      unsavedChanges = true;
    } catch (Exception e) {
      notifyExecutionOnFailure(e.getMessage());
    }
  }

  // Updates the image displayed in the GUI
  @Override
  public void updateDisplay(int[][][] image) {
    gui.updateImage(BufferedImageGenerator.createBufferedImage(image));
  }

  // Updates the histogram displayed in the GUI
  @Override
  public void updateHistogram(int[][] histogram) {
    int[][][] image = new HistogramGenerator(histogram).getImage();
    gui.updateHistogram(BufferedImageGenerator.createBufferedImage(image));
  }
}
