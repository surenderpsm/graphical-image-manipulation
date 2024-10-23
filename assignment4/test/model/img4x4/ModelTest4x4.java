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

  /**
   * Add a red tint by splitting an image, and brightening the green channel by 100.
   */
  @Test
  public void addRedTintTest() {
    int[][][] redTint = get3DArrayFromFile(getRoot() + "redTint.txt");
    model.execute("rgb-split", "image red green blue");
    model.execute("brighten", "100 red brightened-red");
    model.execute("rgb-combine", "original-red-tint brightened-red green blue");
    assertTrue(isEqual(redTint, model.getImage("original-red-tint")));
  }

  /**
   * Add a blue tint by splitting an image, and brightening the green channel by 100.
   */
  @Test
  public void addBlueTintTest() {
    int[][][] blueTint = get3DArrayFromFile(getRoot() + "blueTint.txt");
    model.execute("rgb-split", "image red green blue");
    model.execute("brighten", "100 blue brightened-blue");
    model.execute("rgb-combine", "original-blue-tint red green brightened-blue");
    assertTrue(isEqual(blueTint, model.getImage("original-blue-tint")));
  }

  /**
   * Vertical flip an image, and blur.
   */
  @Test
  public void verticalBlurTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "verticalBlur.txt");
    model.execute("vertical-flip", "image vertical");
    model.execute("blur", "vertical vertical-blur");
    assertTrue(isEqual(expected, model.getImage("vertical-blur")));
  }

  /**
   * Horizontal flip an image, and blur.
   */
  @Test
  public void horizontalBlurTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "horizontalBlur.txt");
    model.execute("horizontal-flip", "image horizontal");
    model.execute("blur", "horizontal horizontal-blur");
    assertTrue(isEqual(expected, model.getImage("horizontal-blur")));
  }

  /**
   * Vertical flip an image, and sharpen.
   */
  @Test
  public void verticalSharpenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "verticalSharpen.txt");
    model.execute("vertical-flip", "image vertical");
    model.execute("sharpen", "vertical vertical-Sharpen");
    assertTrue(isEqual(expected, model.getImage("vertical-Sharpen")));
  }

  /**
   * Horizontal flip an image, and sharpen.
   */
  @Test
  public void horizontalSharpenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "horizontalSharpen.txt");
    model.execute("horizontal-flip", "image horizontal");
    model.execute("sharpen", "horizontal horizontal-Sharpen");
    assertTrue(isEqual(expected, model.getImage("horizontal-Sharpen")));
  }

  //3 cascades

  /**
   * Vertical flip an image, blur then Brighten by 100.
   */
  @Test
  public void verticalBlurBrightenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "verticalBlurBrighten.txt");
    model.execute("vertical-flip", "image vertical");
    model.execute("blur", "vertical vertical-blur");
    model.execute("brighten", "100 vertical-blur vertical-blur-brighten");
    assertTrue(isEqual(expected, model.getImage("vertical-blur-brighten")));
  }

  /**
   * Horizontal flip an image, and blur then Brighten by 100.
   */
  @Test
  public void horizontalBlurBrightenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "horizontalBlurBrighten.txt");
    model.execute("horizontal-flip", "image horizontal");
    model.execute("blur", "horizontal horizontal-blur");
    model.execute("brighten", "100 horizontal-blur horizontal-blur-brighten");
    assertTrue(isEqual(expected, model.getImage("horizontal-blur-brighten")));
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
