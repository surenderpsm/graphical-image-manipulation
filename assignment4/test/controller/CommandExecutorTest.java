//package controller;
//
//import java.io.IOException;
//import model.Model;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.Test.None;
//
///**
// * Unit tests for the {@link CommandExecutor} class.
// */
//public class CommandExecutorTest {
//
//  // The model used for command execution.
//  Model model = new Model();
//
//  /**
//   * Sets up the environment for each test case by initializing the {@link CommandExecutor} with a
//   * sample command.
//   *
//   * @throws Exception if an error occurs during setup.
//   */
//  @Before
//  public void setUp() throws Exception {
//    CommandExecutor ce = new CommandExecutor(model, "load res/img/other/parrot.jpg parrot");
//  }
//
//  /**
//   * Tests that an {@code UnsupportedOperationException} is thrown when a command does not exist.
//   *
//   * @throws IOException if an I/O error occurs during command execution.
//   */
//  @Test(expected = UnsupportedOperationException.class)
//  public void commandDoesNotExist() throws IOException {
//    CommandExecutor ce = new CommandExecutor(model, "darken 10 parrot brighter-parrot");
//  }
//
//  /**
//   * Tests that no exception is thrown when a valid command exists.
//   *
//   * @throws IOException if an I/O error occurs during command execution.
//   */
//  @Test(expected = None.class)
//  public void commandExists() throws IOException {
//    CommandExecutor ce = new CommandExecutor(model, "brighten 10 parrot brighter-parrot");
//  }
//
//  /**
//   * Tests that no exception is thrown for valid input/output commands.
//   *
//   * @throws IOException if an I/O error occurs during command execution.
//   */
//  @Test(expected = None.class)
//  public void testIOCommand() throws IOException {
//    CommandExecutor ce = new CommandExecutor(model, "load res/img/other/parrot.jpg parrot");
//    ce = new CommandExecutor(model, "save res/img/parrot.jpg parrot");
//  }
//
//  /**
//   * Tests that no exception is thrown for valid model-related commands.
//   *
//   * @throws IOException if an I/O error occurs during command execution.
//   */
//  @Test(expected = None.class)
//  public void testModelCommand() throws IOException {
//    CommandExecutor ce = new CommandExecutor(model, "red-component parrot red-parrot");
//  }
//
//  /**
//   * Tests that an {@code IllegalArgumentException} is thrown when invalid arguments are provided to
//   * a command.
//   *
//   * @throws IOException if an I/O error occurs during command execution.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void invalidArguments() throws IOException {
//    CommandExecutor ce = new CommandExecutor(model, "brighten parrot brighter-parrot");
//  }
//
//  /**
//   * Tests that no exception is thrown when valid arguments are provided to a command.
//   *
//   * @throws IOException if an I/O error occurs during command execution.
//   */
//  @Test(expected = None.class)
//  public void validArguments() throws IOException {
//    CommandExecutor ce = new CommandExecutor(model, "blur parrot blurred-parrot");
//  }
//
//}
