package controller;

import controller.viewhandler.ViewAdapter;
import java.io.File;
import java.util.Map;
import java.util.Set;
import model.IModel;
import model.ModelReceiver;
import utils.arguments.ArgumentWrapper;
import utils.arguments.MandatedArgWrapper;
import utils.arguments.Signature;

/**
 * The Controller class is responsible for managing the flow of input and output, handling commands
 * from the user, and interacting with the model. It processes input commands, including script
 * commands and regular commands, and calls the appropriate methods on the model.
 */
public class Controller implements Features {

  private final Map<String, Signature> signatureMap;
  private boolean exit = false;
  private final IModel model;
  private final ViewAdapter vHandler;
  private final IOHandler ioHandler;


  /**
   * Construct controller to handle a GUIImpl.
   *
   * @param model the model to interact with while processing commands.
   */
  public Controller(IModel model, ViewAdapter vHandler) {
    this.model = model;
    this.ioHandler = new IOHandler(model);
    this.vHandler = vHandler;
    this.vHandler.addController(this);
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
    ioHandler.load(file, alias);
    vHandler.notifyExecutionOnSuccess();
  }

  @Override
  public void saveImage(File file, String alias) {
    ioHandler.save(alias, file);
    vHandler.notifyExecutionOnSuccess();
  }

  @Override
  public void invokeCommand(String command, ArgumentWrapper args) {
    try {
      model.execute(command, args);
      vHandler.notifyExecutionOnSuccess();
    } catch (Exception e) {
      vHandler.notifyExecutionOnFailure(e.getMessage());
    }
  }

  @Override
  public void exitApplication() {
    exit = true;
  }

  @Override
  public void shareWith(ModelReceiver receiver) {
    receiver.share(model);
  }
}
