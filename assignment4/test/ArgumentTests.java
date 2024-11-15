import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Test.None;
import utils.arguments.ArgumentType;
import utils.arguments.ArgumentWrapper;
import utils.arguments.FileArgument;
import utils.arguments.IntArgument;
import utils.arguments.MandatedArgWrapper;
import utils.arguments.Signature;
import utils.arguments.StringArgument;

/**
 * Suite of tests for argument package.
 */
public class ArgumentTests {

  /**
   * Test proper construction of Arg wrapper.
   */
  @Test(expected = None.class)
  public void ArgumentWrapperConstruction() {
    ArgumentWrapper aw = new ArgumentWrapper();
    ArgumentWrapper aw2 = new ArgumentWrapper(new StringArgument("hello"),
                                              new FileArgument("/res/imag.png"));
  }

  /**
   * Test proper construction of Mandated arg wrapper.
   */
  @Test(expected = None.class)
  public void MandatedArgumentWrapperConstruction() {
    ArgumentWrapper aw = new MandatedArgWrapper(Signature.define(ArgumentType.INT,
                                                                 ArgumentType.STRING));
  }

  /**
   * Trying to access an integer argument from 0th position when argument is a string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getWithIncorrectArgs() {
    ArgumentWrapper aw = new ArgumentWrapper(new StringArgument("hello"),
                                             new FileArgument("/res/imag.png"));
    aw.getIntArgument(0);
  }

  /**
   * Trying to get an argument from an empty wrapper.
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void getFromEmptyWrapper() {
    ArgumentWrapper argumentWrapper = new ArgumentWrapper();
    argumentWrapper.getIntArgument(0);
  }


  /**
   * Test proper construction of Mandated arg wrapper.
   */
  @Test(expected = None.class)
  public void setArgumentsOnMandateSuccess() {
    ArgumentWrapper aw = new MandatedArgWrapper(Signature.define(ArgumentType.INT,
                                                                 ArgumentType.STRING));
    aw.setArguments(new IntArgument(10), new StringArgument("hello"));
    assertEquals(10, aw.getIntArgument(0));
    assertEquals("hello", aw.getString(1));
  }

  /**
   * Test improper construction of Mandated arg wrapper.
   */
  @Test(expected = IllegalArgumentException.class)
  public void setIncorrectArgsOnMandate() {
    MandatedArgWrapper aw = new MandatedArgWrapper(Signature.define(ArgumentType.INT,
                                                                    ArgumentType.STRING));
    aw.setArgument(0, new StringArgument("hello"));
  }

  /**
   * Test improper construction of Mandated arg wrapper.
   */
  @Test(expected = IllegalArgumentException.class)
  public void setIncorrectArgsOnMandateWithVarArgsSetter() {
    MandatedArgWrapper aw = new MandatedArgWrapper(Signature.define(ArgumentType.INT,
                                                                    ArgumentType.STRING));
    aw.setArguments(new IntArgument(10), new IntArgument(9));
    assertEquals(10, aw.getIntArgument(0));
    assertEquals("hello", aw.getString(1));
  }
}
