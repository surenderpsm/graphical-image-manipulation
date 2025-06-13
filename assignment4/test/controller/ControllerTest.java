package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import controller.viewhandler.ViewAdapter;
import model.Cache;
import model.IModel;
import model.Image;
import model.Model;
import model.command.CommandFactory;
import utils.arguments.ArgumentWrapper;
import utils.arguments.OptionalArgumentKeyword;
import utils.arguments.Signature;
import utils.arguments.StringArgument;

import static org.junit.Assert.assertTrue;

/**
 * class for controller tests.
 */
public class ControllerTest {

  // mock View
  private static class MockView implements ViewAdapter {
    private final StringBuilder log;

    /**
     * constructor of the mock view .
     * @param log log object
     */
    public MockView(StringBuilder log) {
      this.log = log;
    }

    /**
     * method to notify successful execution.
     */
    @Override
    public void notifyExecutionOnSuccess() {
      log.append("notifyExecutionOnSuccess called!").append("\n");
    }

    /**
     * method to notify failure.
     * @param reason reason for the failure.
     */
    @Override
    public void notifyExecutionOnFailure(String reason) {
      log.append("notifyExecutionOnFailure called with: ").append(reason).append("\n");
    }

    /**
     * method to listen for input.
     */
    @Override
    public void listenForInput() {
    }

    /**
     * method to add controller.
     * @param controller object.
     */
    @Override
    public void addController(controller.Features controller) {
    }

  }

  /**
   * mock model class.
   */
  private static class MockModel implements IModel {
    private CommandFactory commandClass = CommandFactory.NONE;
    private final Cache cache = new Cache();
    private final StringBuilder log;
    private boolean shouldThrowException;

    /**
     * constructor of the mock model.
     * @param log log object.
     */
    public MockModel(StringBuilder log) {
      this.log = log;
      this.shouldThrowException = false;
    }

    /**
     * method to set should throw exception.
     */
    public void setShouldThrowException(boolean value) {
      this.shouldThrowException = value;
    }

    /**
     * execute method.
     * @param command the name of the command.
     * @param args    a string of arguments for the command.
     * @throws UnsupportedOperationException error.
     */
    @Override
    public void execute(String command, ArgumentWrapper args) throws UnsupportedOperationException {

      if (shouldThrowException) {
        throw new RuntimeException("Mock error");
      }
      for (CommandFactory c : CommandFactory.values()) {
        if (c.getCommandName().equals(command)) {
          commandClass = c;
          log.append("execute called with command: ").append(command).append("\n");
          runCommand(args);
          return;
        }
      }
      throw new UnsupportedOperationException(
              "User Error: Input command \"" + command + "\" not found.");
    }

    /**
     * Executes the command associated with this model instance using the provided arguments.
     *
     * @param args String arguments to the command being executed.
     */
    private void runCommand(ArgumentWrapper args) {
      commandClass.executeCommandWith(args, cache);
    }

    /**
     * method to get image from the cache.
     * @param name the name of the image to retrieve
     * @return image array.
     */
    @Override
    public int[][][] getImage(String name) {
      log.append("getImage called with name: ").append(name).append("\n");
      return cache.get(name).getImageArray();
    }

    /**
     * set an image object to the cache.
     *
     * @param name  A {@code String} which denotes the image name.
     * @param image A {@code model.Image} object to map to {@code name}.
     */

    @Override
    public void setImage(int[][][] image, String name) {
      log.append("setImage called with name: ").append(name).append("\n");
      cache.set(name, new Image(image));
    }

    /**
     * to check if the histogram object is present in the cache.
     *
     * @param name of the histogram object.
     * @return boolean true or false based on presence.
     */

    @Override
    public boolean isHistogram(String name) {
      return cache.isHistogram(name);
    }


    /**
     * get the required histogram object from the cache.
     *
     * @param name of the histogram object.
     * @return the histogram object.
     * @throws NoSuchElementException if the object is not found in the cache.
     */

    @Override
    public int[][] getHistogram(String name) {
      return cache.getHistogram(name).getHistogram();
    }

    /**
     * get command signatures.
     * @return map of the string names and command signatures.
     */
    @Override
    public Map<String, Signature> getCommandSignatures() {
      return CommandFactory.getSignatureMap();
    }
  }

  private Controller controller;
  private Controller controllerTwo;
  private MockView mockView;
  private MockModel mockModel;
  private File testFile;
  private Model model;
  private StringBuilder log;

  /**
   * set up method.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    mockModel = new MockModel(log);
    model = new Model();
    mockView = new MockView(log);
    controller = new Controller(mockModel, mockView);
    testFile = new File("assignment4/res/img/other/donuts.jpg");
  }

  /**
   * test for load image.
   */
  @Test
  public void testLoadImage() {
    controller.loadImage(testFile, "test-image");
    assertTrue(log.toString().contains("setImage called with name: test-image"));
    assertTrue(log.toString().contains("notifyExecutionOnSuccess called!"));
  }

  /**
   * test for save image.
   */
  @Test
  public void testSaveImage() {
    controller.loadImage(testFile, "test-image");
    assertTrue(log.toString().contains("setImage called with name: test-image"));
    assertTrue(log.toString().contains("notifyExecutionOnSuccess called!"));
    controller.saveImage(testFile, "test-image");
    assertTrue(log.toString().contains("getImage called with name: test-image"));
    assertTrue(log.toString().contains("notifyExecutionOnSuccess called!"));

  }

  /**
   * test for invoke command.
   */
  @Test
  public void testInvokeCommandCommand() {
    controller.loadImage(testFile, "test-image");
    ArgumentWrapper testArgs = new ArgumentWrapper(
            new StringArgument("test-image"),
            new StringArgument("blurred-image"));
    controller.invokeCommand("blur", testArgs);
    assertTrue(log.toString().contains("execute called with command: blur"));
    assertTrue(log.toString().contains("notifyExecutionOnSuccess called!"));

  }

  /**
   * test for invoke command.
   */
  @Test
  public void testInvokeCommandCommandWithSplit() {
    controller.loadImage(testFile, "test-image");
    ArgumentWrapper testArgs = new ArgumentWrapper(
            new StringArgument("test-image"),
            new StringArgument("blurred-image"));
    testArgs.setArgument(OptionalArgumentKeyword.SPLIT, 25);
    controller.invokeCommand("blur", testArgs);
    assertTrue(log.toString().contains("execute called with command: blur"));
    assertTrue(log.toString().contains("notifyExecutionOnSuccess called!"));

  }

  /**
   * test for invoke command.
   */
  @Test
  public void testInvokeCommandInvalidCommand() {
    ArgumentWrapper testArgs = new ArgumentWrapper();

    controller.invokeCommand("blurr", testArgs);
    assertTrue(
            log.toString().contains("notifyExecutionOnFailure called with: User "
                    + "Error: Input command \"blurr\" not found."));

  }

  /**
   * test for invoke command.
   */
  @Test
  public void testInvokeCommandInvalidArgs() {
    ArgumentWrapper testArgs = new ArgumentWrapper();

    controller.invokeCommand("blur", testArgs);
    assertTrue(log.toString().contains("notifyExecutionOnFailure called "
            + "with: ERROR : Expected 2 arguments."));

  }

  /**
   * test for get command names.
   */
  @Test
  public void testGetCommandNames() {
    Set<String> commandNames = controller.getCommandNames();
    assertTrue("Should contain 'blur' command",
            commandNames.contains("blur"));
    assertTrue("Should contain 'sharpen' command",
            commandNames.contains("sharpen"));
  }

  /**
   * test exit application.
   */
  @Test
  public void testExitApplication() {
    controller.exitApplication();

    // Note: In a real scenario, you might want to verify
    // the exit flag is set or other exit-related behaviors
  }


}