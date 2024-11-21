package view.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.SwingUtilities;
import utils.arguments.ArgumentWrapper;
import utils.arguments.IntArgument;
import view.gui.frame.DefaultFrame;
import view.gui.frame.UpdateObserver;

/**
 * The {@code GUIImpl} class is an implementation of the {@link GUI} interface and acts as a facade
 * for the graphical user interface. It communicates with an observer to execute user actions and
 * updates the view through the {@link UpdateObserver}. This class manages the GUI state, including
 * preview and confirmation modes, and handles user-initiated commands.
 */
public class GUIImpl implements GUI, ComponentObserver {

  private GUIObserver observer;
  private UpdateObserver view;
  private boolean preview = false;
  private int split = 0;
  private ArgumentWrapper currentArgs;
  private String currentCommand;
  private boolean confirmed = true;

  /**
   * Constructs a {@code GUIImpl} instance and initializes the GUI components on the Swing event
   * dispatch thread. The GUI starts in non-preview mode by default.
   */
  public GUIImpl() {
    SwingUtilities.invokeLater(() -> {
      this.view = new DefaultFrame(this);
      setPreviewMode(false);
    });
  }

  @Override
  public void addObserver(GUIObserver observer) {
    this.observer = observer;
  }

  @Override
  public void updateImage(BufferedImage image) {
    view.updateImage(image);
  }

  @Override
  public void updateHistogram(BufferedImage histogram) {
    view.updateHistogram(histogram);
  }

  @Override
  public void displayError(String message) {
    view.displayError(message);
  }

  @Override
  public int getSplit() {
    return split;
  }

  @Override
  public void enableAllFeatures() {
    view.enableAllFeatures();
  }

  @Override
  public boolean isPreviewMode() {
    return preview;
  }

  @Override
  public boolean isConfirmed() {
    return confirmed;
  }

  @Override
  public void loadImage(File file) {
    if (file != null) {
      observer.onLoadImage(file);
    }
  }

  @Override
  public void saveImage(File file) {
    resetComponents();
    if (file != null) {
      observer.onSaveImage(file);
    }
  }

  @Override
  public void brighten(int value) {
    currentCommand = "brighten";
    setPreviewMode(true);
    currentArgs = new ArgumentWrapper(new IntArgument(value));
    runCommand();
  }

  @Override
  public void blur() {
    currentCommand = "blur";
    setPreviewMode(true);
    runEmptyArgCommand();
  }

  @Override
  public void sharpen() {
    currentCommand = "sharpen";
    setPreviewMode(true);
    runEmptyArgCommand();
  }

  @Override
  public void hFlip() {
    currentCommand = "horizontal-flip";
    setPreviewMode(false);
    runEmptyArgCommand();
  }

  @Override
  public void vFlip() {
    currentCommand = "vertical-flip";
    setPreviewMode(false);
    runEmptyArgCommand();
  }

  @Override
  public void sepia() {
    currentCommand = "sepia";
    setPreviewMode(true);
    runEmptyArgCommand();
  }

  @Override
  public void grayscale() {
    currentCommand = "grayscale";
    setPreviewMode(true);
    runEmptyArgCommand();
  }

  @Override
  public void redComponent() {
    currentCommand = "red-component";
    preview = true;
    runEmptyArgCommand();
  }

  @Override
  public void blueComponent() {
    currentCommand = "blue-component";
    preview = true;
    runEmptyArgCommand();
  }

  @Override
  public void greenComponent() {
    currentCommand = "green-component";
    preview = true;
    runEmptyArgCommand();
  }

  @Override
  public void compress(int ratio) {
    currentCommand = "compress";
    setPreviewMode(false);
    currentArgs = new ArgumentWrapper(new IntArgument(ratio));
    runCommand();
  }

  @Override
  public void colorCorrect() {
    currentCommand = "color-correct";
    setPreviewMode(true);
    runEmptyArgCommand();
  }

  @Override
  public void downscale(int h, int w) {
    currentCommand = "downscale";
    setPreviewMode(false);
    currentArgs = new ArgumentWrapper(new IntArgument(h), new IntArgument(w));
    runCommand();
  }

  @Override
  public void adjustLevels(int b, int m, int w) {
    currentCommand = "levels-adjust";
    setPreviewMode(true);
    currentArgs = new ArgumentWrapper(new IntArgument(b), new IntArgument(m), new IntArgument(w));
    runCommand();
  }


  /**
   * Runs a command with no arguments.
   */
  private void runEmptyArgCommand() {
    currentArgs = new ArgumentWrapper();
    runCommand();
  }

  /**
   * Executes the current command with the associated arguments. Notifies the observer of the
   * command execution.
   */
  private void runCommand() {
    if (currentCommand != null) {
      observer.onCommandExecuted(currentCommand, currentArgs.clone());
    }
    this.confirmed = true;
  }

  @Override
  public void splitLevel(int split) {
    this.split = split;
    runCommand();
  }

  @Override
  public void setPreviewMode(boolean preview) {
    this.preview = preview;
    view.setPreview(preview);
    view.setChannelSettings(!preview);
  }

  @Override
  public void setConfirmation(boolean confirm) {
    this.confirmed = confirm;
    runCommand();
  }

  @Override
  public boolean quit() {
    return observer.requestApplicationExit();
  }

  @Override
  public void resetComponents() {
    this.confirmed = false;
    this.preview = false;
    runCommand();
  }
}
