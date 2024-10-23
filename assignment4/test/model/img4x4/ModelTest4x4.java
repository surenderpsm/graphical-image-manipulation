package model.img4x4;

import static org.junit.Assert.assertTrue;

import model.AbstractModelTest;
import org.junit.Test;

public class ModelTest4x4 extends AbstractModelTest {

  @Override
  protected String getRoot() {
    return "assignment4/test/model/img4x4/";
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
