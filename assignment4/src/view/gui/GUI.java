package view.gui;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.SwingUtilities;
import utils.arguments.ArgumentWrapper;
import utils.arguments.IntArgument;

/**
 *
 */
public class GUI implements GUIHandlingObject, ViewComponentListener {

  private final GUIViewListener controller;
  private UpdateObserver view;
  private boolean preview = false;
  private int split = 0;
  private ArgumentWrapper currentArgs;
  private String currentCommand;
  private boolean confirmed = true;

  public GUI(GUIViewListener controller) {
    this.controller = controller;
    SwingUtilities.invokeLater(() -> {
      this.view = new DefaultFrame(this);
      setPreviewMode(false);
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
      controller.onLoadImage(file);
    }
  }

  @Override
  public void saveImage(File file) {
    resetComponents();
    if (file != null) {
      controller.onSaveImage(file);
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

  private void runEmptyArgCommand() {
    currentArgs = new ArgumentWrapper();
    runCommand();
  }

  private void runCommand() {
    if (currentCommand != null) {
      controller.onCommandExecuted(currentCommand, currentArgs.clone());
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
    return controller.requestApplicationExit();
  }

  @Override
  public void resetComponents() {
    this.confirmed = false;
    this.preview = false;
    runCommand();
  }
}
