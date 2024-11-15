package controller;

import controller.viewhandler.CLIHandler;
import controller.viewhandler.GUIHandler;
import controller.viewhandler.ViewHandler;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import model.IModel;
import utils.arguments.ArgumentWrapper;
import utils.arguments.MandatedArgWrapper;
import utils.arguments.Signature;

/**
 * The Controller class is responsible for managing the flow of input and output, handling commands
 * from the user, and interacting with the model. It processes input commands, including script
 * commands and regular commands, and calls the appropriate methods on the model.
 */
public class Controller implements IControllerView {

  private final Map<String, Signature> signatureMap;
  private boolean exit = false;
  private final IModel model;
  private final ViewHandler vHandler;
  private final IOHandler ioHandler;


  /**
   * Construct controller to handle a GUI.
   *
   * @param model the model to interact with while processing commands.
   */
  public Controller(IModel model) {
    this.model = model;
    this.ioHandler = new IOHandler(model);
    this.vHandler = new GUIHandler(this);
    this.signatureMap = model.getCommandSignatures();
  }

  /**
   * Construct controller to handle CLI.
   *
   * @param model the model to interact with while processing commands.
   * @param in    input stream.
   * @param out   print stream.
   */
  public Controller(IModel model, InputStream in, PrintStream out) {
    this.model = model;
    this.ioHandler = new IOHandler(model);
    this.vHandler = new CLIHandler(in,
                                   out,
                                   this);
    this.signatureMap = model.getCommandSignatures();
  }

  /**
   * Main method to run the controller. It processes commands in a loop until the user quits. It
   * handles both individual commands and script files.
   */
  public void run() {
    do {
      vHandler.listenForInput();
    }
    while (!exit);
  }

  @Override
  public Set<String> getCommandNames() {
    return signatureMap.keySet();
  }

  @Override
  public MandatedArgWrapper getMandatedArgs(String commandName) {
    if (!signatureMap.containsKey(commandName)) {
      throw new IllegalArgumentException("Invalid command name: " + commandName);
    }
    return new MandatedArgWrapper(signatureMap.get(commandName));
  }

  @Override
  public void loadImage(File file, String alias) {
    ioHandler.load(file,
                   alias);
    vHandler.notifyExecutionOnSuccess();
  }

  @Override
  public void saveImage(File file, String alias) {
    ioHandler.save(alias,
                   file);
    vHandler.notifyExecutionOnSuccess();
  }

  @Override
  public void invokeCommand(String command, ArgumentWrapper args) {
    try {
      model.execute(command,
                    args);
      vHandler.notifyExecutionOnSuccess();
    } catch (Exception e) {
      vHandler.notifyExecutionOnFailure(e.getMessage());
    }
  }

  @Override
  public void exitApplication() {
    exit = true;
  }
}
