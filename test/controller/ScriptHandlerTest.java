package controller;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Arrays;
import org.junit.Test;

/**
 * Unit tests for the {@link ScriptHandler} class.
 */

public class ScriptHandlerTest {

  // Root directory where script.txt files are located.
  String root = "res/scripts/";

  /**
   * Tests if comments in the script.txt file are ignored and only valid commands are retrieved.
   *
   * @throws FileNotFoundException if the script.txt file is not found.
   */
  @Test
  public void commentsAreIgnored() throws FileNotFoundException {
    String script = root + "script_with_comments";
    ScriptHandler sh = new ScriptHandler(script);
    // There are 2 commands in the script.txt. 2 comments that were present were ignored.
    assertEquals(2, sh.getCommands().size());
  }

  /**
   * Tests if the script.txt file contains no commands, an empty list is returned.
   *
   * @throws FileNotFoundException if the script.txt file is not found.
   */
  @Test
  public void scriptIsEmpty() throws FileNotFoundException {
    String script = root + "empty_script";
    ScriptHandler sh = new ScriptHandler(script);
    // No commands found in the script.txt.
    assertEquals(0, sh.getCommands().size());
  }

  /**
   * Tests if all valid commands from the script.txt file are correctly retrieved.
   *
   * @throws FileNotFoundException if the script.txt file is not found.
   */
  @Test
  public void allCommandsAreRetrieved() throws FileNotFoundException {
    String script = root + "script_with_comments";
    ScriptHandler sh = new ScriptHandler(script);
    String[] commands = new String[]{
        "load res/img/parrot.jpg parrot",
        "brighten 10 parrot " + "brighter-parrot"};
    // Verify all commands are retrieved.
    assertEquals(Arrays.asList(commands), sh.getCommands());
  }

  /**
   * Tests if a {@link FileNotFoundException} is thrown when the script.txt file does not exist.
   *
   * @throws IllegalArgumentException when trying to load a non-existent script.txt file.
   */
  @Test(expected = IllegalArgumentException.class)
  public void commandFileDoesNotExist() {
    String script = root + "non_existing_script";
    ScriptHandler sh = new ScriptHandler(script);
    sh.getCommands();
  }


}
