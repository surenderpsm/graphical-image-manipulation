package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import controller.viewhandler.ViewHandler;
import model.Cache;
import model.IModel;
import model.Image;
import model.Model;
import utils.arguments.ArgumentWrapper;
import utils.arguments.Signature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ControllerTest {

  private static class MockViewHandler implements ViewHandler {
    public boolean successNotified = false;
    public boolean failureNotified = false;
    public String failureReason = null;

    @Override
    public void notifyExecutionOnSuccess() {
      successNotified = true;
    }

    @Override
    public void notifyExecutionOnFailure(String reason) {
      failureNotified = true;
      failureReason = reason;
    }

    @Override
    public void listenForInput() {
    }

    @Override
    public void addController(controller.Features controller) {
    }

    public void reset() {
      successNotified = false;
      failureNotified = false;
      failureReason = null;
    }
  }

  private static class MockModel implements IModel {

    public boolean executeCalled = false;
    public String lastCommand = null;
    public ArgumentWrapper lastArgs = null;
    private final Cache cache = new Cache();

    public StringBuilder log = new StringBuilder();

    @Override
    public Map<String, Signature> getCommandSignatures() {
      return Map.of();
    }

    @Override
    public boolean isHistogram(String name) {
      return false;
    }

    @Override
    public int[][] getHistogram(String name) throws NoSuchElementException {
      return new int[0][];
    }

    @Override
    public int[][][] getImage(String name) throws NoSuchElementException {
      return new int[0][][];
    }

    @Override
    public void setImage(int[][][] image, String name) {
      cache.set(name, new Image(image));
      log.append(name);
    }

    @Override
    public void execute(String command, ArgumentWrapper args) throws UnsupportedOperationException {
      executeCalled = true;
      lastCommand = command;
      lastArgs = args;
      log.append(command);
    }
  }

  private Controller controller;
  private Controller controllerTwo;
  private MockViewHandler mockViewHandler;
  private MockModel mockModel;
  private File testFile;
  private Model model;

  @Before
  public void setUp() {
    mockModel = new MockModel();
    model = new Model();
    mockViewHandler = new MockViewHandler();
    controller = new Controller(mockModel, mockViewHandler);
    controllerTwo = new Controller(model, mockViewHandler);
    testFile = new File("assignment4/res/img/other/donuts.jpg");
  }

  @Test
  public void testLoadImage() {
    controllerTwo.loadImage(testFile, "test-image");

    assertTrue("View should be notified of successful load",
            mockViewHandler.successNotified);
  }

  @Test
  public void testSaveImage() {
    int[][][] originalImage = {{{23, 24, 25}}};
    //get3DArrayFromFile("assignment4/res/img4x4/original.txt");
    model.setImage(originalImage, "test-image");

    controllerTwo.saveImage(testFile, "test-image");

    assertTrue("View should be notified of successful save", mockViewHandler.successNotified);
  }

  @Test
  public void testInvokeCommand() {
    ArgumentWrapper testArgs = new ArgumentWrapper();

    controller.invokeCommand("blur", testArgs);

    assertTrue("Model should execute the command",
            mockModel.executeCalled);
    assertEquals("Correct command should be executed",
            "blur", mockModel.lastCommand);
    assertTrue("View should be notified of successful execution",
            mockViewHandler.successNotified);
  }

  @Test
  public void testGetCommandNames() {
    Set<String> commandNames = controllerTwo.getCommandNames();
    assertTrue("Should contain 'blur' command",
            commandNames.contains("blur"));
    assertTrue("Should contain 'sharpen' command",
            commandNames.contains("sharpen"));
  }

  @Test
  public void testExitApplication() {

    controller.exitApplication();

    // Note: In a real scenario, you might want to verify
    // the exit flag is set or other exit-related behaviors
  }


}