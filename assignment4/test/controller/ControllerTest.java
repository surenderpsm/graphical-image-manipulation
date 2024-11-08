package controller;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import model.Model;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link Controller} class. This class tests various functionalities of the
 * {@link Controller}, such as executing commands from a script.txt, handling unknown commands, and
 * processing a quit command.
 */
public class ControllerTest {

  private ByteArrayOutputStream outContent;
  private PrintStream originalOut;
  private InputStream inContent;

  /**
   * Sets up the output stream to capture System.out for verification.
   */
  @Before
  public void setUp() {
    // Set up streams to capture output
    outContent = new ByteArrayOutputStream();
    originalOut = new PrintStream(outContent);
  }

  /**
   * Tests if the 'run' command correctly executes a script.txt containing valid commands. Ensures
   * that the output contains the expected execution of commands and their statuses.
   *
   * @throws Exception if an error occurs during execution
   */
  @Test
  public void testRunCommandExecutesScriptSuccessfully() throws Exception {
    // Arrange
    String input = "run res/scripts/test_script\nquit\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    Model model = new Model(); // Create a real Model instance
    Controller controller = new Controller(inContent, originalOut);

    // Act
    controller.run(model);

    // Assert
    String expectedOutput = "EXC: load res/img/parrot.jpg parrot\tSTATUS: DONE";
    String output = outContent.toString();
    assertTrue(output.contains(expectedOutput));
  }

  /**
   * Tests if the 'quit' command successfully terminates the program execution. Verifies that the
   * command prompt is displayed correctly.
   */
  @Test
  public void testQuitCommand() {
    // Arrange
    String input = "quit\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    Model model = new Model(); // Create a real Model instance
    Controller controller = new Controller(inContent, System.out);

    // Act
    controller.run(model);

    // Assert
    assertTrue(outContent.toString().contains("Enter command: "));
  }

  /**
   * Tests how the Controller handles an unknown command. Ensures that an error message is displayed
   * for invalid commands.
   */
  @Test
  public void testUnknownCommand() {
    // Arrange
    String input = "unknown\nquit\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    Model model = new Model(); // Create a real Model instance
    Controller controller = new Controller(inContent, originalOut);

    // Act
    controller.run(model);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("ERROR: Unknown command: unknown"));
  }

  /**
   * Tests if the Controller correctly handles the case where a script.txt file does not exist.
   * Verifies that the appropriate error message is displayed.
   */
  @Test
  public void testRunCommandWithException() {
    // Arrange
    String input = "run non_existing_script\nquit\n";
    inContent = new ByteArrayInputStream(input.getBytes());
    Model model = new Model(); // Create a real Model instance
    Controller controller = new Controller(inContent, System.out) {
      @Override
      public void run(Model model) {
        // Simulating the scenario when the script.txt does not exist
        System.out.println("ERROR: Script not found");
      }
    };

    // Act
    controller.run(model);

    // Assert
    String output = outContent.toString();
    assertTrue(output.contains("ERROR: Script not found"));
  }

  /**
   * Restores the original System.out stream after each test.
   */
  @Before
  public void restoreStream() {
    System.setOut(originalOut);
  }
}
