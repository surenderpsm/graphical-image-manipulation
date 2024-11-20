package view.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.SwingUtilities;
import utils.arguments.ArgumentWrapper;
import utils.arguments.IntArgument;

public class GUI implements GUIHandlingObject, ViewComponentListener {

  private final GUIViewListener controller;
  private UpdateObserver view;
  private boolean preview = false;
  private int split = 0;
  private ArgumentWrapper currentArgs;
  private String currentCommand;
  private boolean confirmed;

  public GUI(GUIViewListener controller) {
    this.controller = controller;
    SwingUtilities.invokeLater(() -> {
      this.view = new DefaultFrame(this);
    });
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
    // @todo get split from the user. get the slider value.
    return split;
  }

  @Override
  public void registerListeners() {
    // @todo need to remove is not used.
  }

  @Override
  public void enableAllFeatures() {
    view.enableAllFeatures();
  }

  @Override
  public boolean isPreviewMode() {
    //@todo the confirm button listener can turn off preview mode.
    return preview;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  @Override
  public void loadImage(File file) {
    if (file != null) {
      controller.onLoadImage(file);
      preview = true;
    }
  }

  @Override
  public void saveImage(File file) {
    if (file != null) {
      controller.onSaveImage(file);
    }
  }

  @Override
  public void brighten(int value) {
    currentCommand = "brighten";
    currentArgs = new ArgumentWrapper(new IntArgument(value));
    runCommand();
  }

  @Override
  public void blur() {
    currentCommand = "blur";
    runEmptyArgCommand();
  }

  @Override
  public void sharpen() {
    currentCommand = "sharpen";
    runEmptyArgCommand();
  }

  @Override
  public void hFlip() {
    currentCommand = "horizontal-flip";
    runEmptyArgCommand();
  }

  @Override
  public void vFlip() {
    currentCommand = "vertical-flip";
    runEmptyArgCommand();
  }

  @Override
  public void sepia() {
    currentCommand = "sepia";
    runEmptyArgCommand();
  }

  @Override
  public void grayscale() {
    currentCommand = "grayscale";
    runEmptyArgCommand();
  }

  @Override
  public void redComponent() {
    currentCommand = "red-component";
    runEmptyArgCommand();
  }

  @Override
  public void blueComponent() {
    currentCommand = "blue-component";
    runEmptyArgCommand();
  }

  @Override
  public void greenComponent() {
    currentCommand = "green-component";
    runEmptyArgCommand();
  }

  @Override
  public void compress(int ratio) {
    currentCommand = "compress";
    currentArgs = new ArgumentWrapper(new IntArgument(ratio));
    runCommand();
  }

  @Override
  public void colorCorrect() {
    currentCommand = "color-correct";
    runEmptyArgCommand();
  }

  @Override
  public void downscale(int h, int w) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void adjustLevels(int b, int m, int w) {
    currentCommand = "adjust-levels";
    currentArgs = new ArgumentWrapper(new IntArgument(b), new IntArgument(m), new IntArgument(w));
    runCommand();
  }

  private void runEmptyArgCommand() {
    currentArgs = new ArgumentWrapper();
    runCommand();
  }

  private void runCommand() {
    if (currentCommand != null) {
      System.out.println("being cloned...");
      controller.onCommandExecuted(currentCommand, currentArgs.clone());
    }
  }

  @Override
  public void splitLevel(int split) {
    this.split = split;
    runCommand();
  }

  @Override
  public void setPreviewMode(boolean preview) {
    this.preview = preview;
    runCommand();
  }

  @Override
  public void setConfirmation(boolean confirm) {
    this.confirmed = confirm;
    runCommand();
    confirm = true;
  }

  @Override
  public boolean quit() {
    return controller.requestApplicationExit();
  }
}
