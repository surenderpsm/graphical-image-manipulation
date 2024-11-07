import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

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

  /**
   * test for compression.
   */
  @Test
  public void compressionTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "compression.txt");
    model.execute("compress", "60 image compressed");
    int[][][] arr = model.getImage("compressed");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for invalid input compression.
   */
  @Test (expected = IllegalArgumentException.class)
  public void compressionTest2() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "compression.txt");
    model.execute("compress", "0.60 image compressed");
  }

  /**
   * test for histogram.
   */
  @Test
  public void histogramTest() {
    int[][] expected = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1
        , 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0
        , 1, 0, 0, 0, 0, 0, 2, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0
        , 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    }, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0
        , 0, 1, 1, 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 1, 0, 0, 2, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1
        , 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        , 0, 0, 0, 0, 0, 0, 0}};
    model.execute("histogram", "image histogram");
    int[][] arr = model.getHistogram("histogram");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual2D(expected, arr));
  }

  /**
   * test for color correction.
   */
  @Test
  public void ColorCorrectionTest() {
    int[][][] expected = {{{142, 119, 122}, {147, 133, 140}, {155, 152, 153}, {113, 112, 126}},
        {{135, 111, 119}, {119, 119, 141}, {134, 158, 170}, {120, 131, 144}},
        {{109, 104, 129}, {89, 93, 129}, {123, 113, 133}, {143, 163, 156}},
        {{88, 103, 131}, {83, 82, 119}, {153, 149, 150}, {119, 128, 135}}};

    model.execute("color-correct", "image colorCorrected");
    int[][][] arr = model.getImage("colorCorrected");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for levels adjust.
   */
  @Test
  public void levelsAdjustTest() {
    int[][][] expected  =  {
        {{199, 95, 90}, {204, 109, 108}, {212, 128, 121}, {170, 88, 94}},
        {{192, 87, 87}, {176, 95, 109}, {191, 134, 138}, {177, 107, 112}},
        {{166, 80, 97}, {146, 69, 97}, {180, 89, 101}, {200, 139, 124}},
        {{145, 79, 99}, {140, 58, 87}, {210, 125, 118}, {176, 104, 103}}
    };

    model.execute("levels-adjust", "0 128 255 image levelAdjusted");
    // levels-adjust b m w image-name dest-image-name"
    int[][][] arr = model.getImage("levelAdjusted");
    System.out.println(Arrays.deepToString(arr));
    System.out.println(Arrays.deepToString(expected));
    assertTrue(isEqual(expected, arr));
  }

}