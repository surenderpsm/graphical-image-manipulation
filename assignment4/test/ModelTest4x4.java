import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the model on a 4x4 matrix image stored as an array in res/img4x4/original.txt. Expected
 * outputs are also stored as arrays in text files.
 */
public class ModelTest4x4 extends AbstractModelTest {

  @Override
  protected String getRoot() {
    return "res/img4x4/";
  }

  /**
   * Vertical flip an image, and sharpen then Brighten by 100.
   */
  @Test
  public void verticalSharpenBrightenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "verticalSharpenBrighten.txt");
    model.execute("vertical-flip", "image vertical");
    model.execute("sharpen", "vertical vertical-Sharpen");
    model.execute("brighten", "100 vertical-Sharpen vertical-Sharpen-brighten");
    assertTrue(isEqual(expected, model.getImage("vertical-Sharpen-brighten")));
  }

  /**
   * Horizontal flip an image, and sharpen then Brighten by 100.
   */
  @Test
  public void horizontalSharpenBrightenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "horizontalSharpenBrighten.txt");
    model.execute("horizontal-flip", "image horizontal");
    model.execute("sharpen", "horizontal horizontal-Sharpen");
    model.execute("brighten", "100 horizontal-Sharpen horizontal-Sharpen-brighten");
    assertTrue(isEqual(expected, model.getImage("horizontal-Sharpen-brighten")));
  }

}
