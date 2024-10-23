package model.img4x4;

import static org.junit.Assert.assertTrue;

import model.AbstractModelTest;
import org.junit.Test;

public class ModelTest4x4 extends AbstractModelTest {

  @Override
  protected String getRoot() {
    return "assignment4/test/model/img4x4/";
  }

  @Test
  public void brightenBlurTest() {
    int[][][] brightenBlur = get3DArrayFromFile(getRoot() + "brightenBlur.txt");
    model.execute("brighten", "10 image brightened");
    model.execute("blur", "brightened brightened-blurred");
    assertTrue(isEqual(brightenBlur, model.getImage("brightened-blurred")));
  }

  /**
   * Add a green tint by splitting an image, and brightening the green channel by 100.
   */
  @Test
  public void addGreenTintTest() {
    int[][][] greenTint = get3DArrayFromFile(getRoot() + "greenTint.txt");
    model.execute("rgb-split", "image red green blue");
    model.execute("brighten", "100 green brightened-green");
    model.execute("rgb-combine", "original-green-tint red brightened-green blue");
    assertTrue(isEqual(greenTint, model.getImage("original-green-tint")));
  }

}
