import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

import utils.arguments.ArgumentWrapper;
import utils.arguments.IntArgument;
import utils.arguments.OptionalArgumentKeyword;
import utils.arguments.StringArgument;

/**
 * Tests for the model on a 4x4 matrix image stored as an array in res/img4x4/original.txt. Expected
 * outputs are also stored as arrays in text files.
 */
public class ModelTest4x4 extends AbstractModelTest {

  @Override
  protected String getRoot() {
    return "assignment4/res/img4x4/";
  }

//  /**
//   * Vertical flip an image, and sharpen then Brighten by 100.
//   */
//  @Test
//  public void verticalSharpenBrightenTest() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "verticalSharpenBrighten.txt");
//    model.execute("vertical-flip", "image vertical");
//    model.execute("sharpen", "vertical vertical-Sharpen");
//    model.execute("brighten", "100 vertical-Sharpen vertical-Sharpen-brighten");
//    assertTrue(isEqual(expected, model.getImage("vertical-Sharpen-brighten")));
//  }
//
//  /**
//   * Horizontal flip an image, and sharpen then Brighten by 100.
//   */
//  @Test
//  public void horizontalSharpenBrightenTest() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "horizontalSharpenBrighten.txt");
//    model.execute("horizontal-flip", "image horizontal");
//    model.execute("sharpen", "horizontal horizontal-Sharpen");
//    model.execute("brighten", "100 horizontal-Sharpen horizontal-Sharpen-brighten");
//    assertTrue(isEqual(expected, model.getImage("horizontal-Sharpen-brighten")));
//  }
//
//  /**
//   * test for compression.
//   */
//  @Test
//  public void compressionTest() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "compression.txt");
//    model.execute("compress", "60 image compressed");
//    int[][][] arr = model.getImage("compressed");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * test for invalid input compression.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void compressionTest2() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "compression.txt");
//    model.execute("compress", "0.60 image compressed");
//  }
//

  /**
   * test for histogram.
   */
  @Test
  public void histogramTest() {
    int[][] expected = get2DArrayFromFile(getRoot() + "histogram.txt");
    model.execute("histogram",
                  new ArgumentWrapper(new StringArgument("image"),
                                      new StringArgument("histogram")));
    int[][] arr = model.getHistogram("histogram");
    assertTrue(isEqual2D(expected, arr));
  }

  /**
   * test for color correction.
   */
  @org.junit.jupiter.api.Test
  public void ColorCorrectionTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "colorCorrection.txt");
    model.execute("color-correct",
                  new ArgumentWrapper(new StringArgument("image"),
                                      new StringArgument("colorCorrected")));
    int[][][] arr = model.getImage("colorCorrected");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for levels adjust.
   */
  @Test
  public void levelsAdjustTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "levelsAdjust.txt");
    // levels-adjust b m w image-name dest-image-name
    model.execute("levels-adjust",
                  new ArgumentWrapper(new IntArgument(0),
                                      new IntArgument(128),
                                      new IntArgument(255),
                                      new StringArgument("image"),
                                      new StringArgument("levelAdjusted")));
    int[][][] arr = model.getImage("levelAdjusted");
    assertTrue(isEqual(expected, arr));
  }
//
//  /**
//   * Apply "brighten 50" on only first 50% of the image.
//   */
//  @Test
//  public void splitOnBrightenTest() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "brighten50split50.txt");
//    model.execute("brighten", "50 image brighten-by-50-split split 50");
//    int[][][] arr = model.getImage("brighten-by-50-split");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply "luma" on 25% of image.
//   */
//  @Test
//  public void splitOnLumaComponent() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "lumaSplit25.txt");
//    model.execute("luma-component", "image luma-split-25 split 25");
//    int[][][] arr = model.getImage("luma-split-25");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply "brighten -10" on 100% of image.
//   */
//  @Test
//  public void splitOnDarkenTest() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "darken.txt");
//    model.execute("brighten", "-10 image darkened split 100");
//    int[][][] arr = model.getImage("darkened");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply "color-correct" on 75% of image.
//   */
//  @Test
//  public void splitOnColorCorrect() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "colorCorrectSplit75.txt");
//    model.execute("color-correct", "image colorCorrected split 75");
//    int[][][] arr = model.getImage("colorCorrected");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply "levels-adjust" on 20% of the image.
//   */
//  @Test
//  public void splitOnLevelAdjust() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "levelsAdjustSplit25.txt");
//    model.execute("levels-adjust", "0 128 255 image levelAdjusted split 20");
//    int[][][] arr = model.getImage("levelAdjusted");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply "value-component" on 0% of the image.
//   */
//  @Test
//  public void splitOnValueComponent() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "original.txt");
//    model.execute("value-component", "image valueComponent split 0");
//    int[][][] arr = model.getImage("valueComponent");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply intensity-component on 90% of the image.
//   */
//  @Test
//  public void splitOnIntensityComponent() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "IntensityComponentSplit75.txt");
//    model.execute("intensity-component", "image intensityComponent split 90");
//    int[][][] arr = model.getImage("intensityComponent");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply split operation on horizontal flip.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void splitOnHorizontalFlip() {
//    model.execute("horizontal-flip", "image horizontal-flip split 10");
//  }
//
//  /**
//   * Apply split operation on vertical flip.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void splitOnVerticalFlip() {
//    model.execute("vertical-flip", "image vertical-flip split 10");
//  }
//
//  /**
//   * Apply split operation on red component.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void splitOnRedComponent() {
//
//    model.execute("red-component", "image rimage split 10");
//  }
//
//  /**
//   * Apply split operation on green component.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void splitOnGreenComponent() {
//
//    model.execute("green-component", "image gimage split 10");
//  }
//
//  /**
//   * Apply split operation on blue component.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void splitOnBlueComponent() {
//
//    model.execute("blue-component", "image bimage split 10");
//  }
//
//  /**
//   * Add split on Histogram.
//   */
//  @Test(expected = IllegalArgumentException.class)
//  public void splitOnHistogram() {
//
//    model.execute("histogram", "image histogram split 10");
//  }
//
//  /**
//   * Apply blur on 25% of the image.
//   */
//  @Test
//  public void splitOnBlur() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "BlurSplit25.txt");
//    model.execute("blur", "image blurSplit split 25");
//    int[][][] arr = model.getImage("blurSplit");
//    assertTrue(isEqual(expected, arr));
//  }
//
//  /**
//   * Apply blur on 50% of the image.
//   */
//  @Test
//  public void splitOnSharpen() {
//    int[][][] expected = get3DArrayFromFile(getRoot() + "SharpenSplit50.txt");
//    model.execute("sharpen", "image sharpenSplit split 50");
//    int[][][] arr = model.getImage("sharpenSplit");
//    assertTrue(isEqual(expected, arr));
//  }
//

  /**
   * Apply Sepia tone on 75% of the image.
   */
  @Test
  public void splitOnSepia() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "SepiaSplit75.txt");
//    model.execute("sepia", "image sepiaSplit split 75");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("sepiaSplit"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 75);
    model.execute("sepia", argumentWrapper);

    int[][][] arr = model.getImage("sepiaSplit");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test
  public void TestDownscale() {
    int[][][] expected = {{{199, 95, 90}, {212, 128, 121}}, {{166, 80, 97}, {180, 89, 101}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(2),
            new IntArgument(2),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    //System.out.println(Arrays.deepToString(arr));
    //System.out.println(Arrays.deepToString(expected));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void TestInvalidDownscale() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(4),
            new IntArgument(5),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void TestInvalidDownscale2() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(0),
            new IntArgument(0),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void TestInvalidDownscale3() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(7),
            new IntArgument(14),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void TestInvalidDownscale4() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(0),
            new IntArgument(14),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void TestInvalidDownscale5() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(23),
            new IntArgument(0),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test
  public void TestDownscale2() {
    int[][][] expected = {{{199, 95, 90}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(1),
            new IntArgument(1),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    //System.out.println(Arrays.deepToString(expected));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test
  public void TestDownscale3() {
    int[][][] expected = {{{199, 95, 90}, {0, 0, 0}, {0, 0, 0}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(3),
            new IntArgument(1),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    //System.out.println(Arrays.deepToString(expected));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test
  public void TestDownscale4() {
    int[][][] expected = {{{199, 95, 90}}, {{166, 80, 97}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(1),
            new IntArgument(2),new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    System.out.println(Arrays.deepToString(arr));
    //System.out.println(Arrays.deepToString(expected));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingBlur() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialBlur.txt");;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("blurred"));
    model.execute("blur", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("blurred"),
            new StringArgument("imageMask"),
            new StringArgument("PartialBlurred"));
    model.execute("partial-process",argumentWrapper2);
    int[][][] arr = model.getImage("PartialBlurred");
    System.out.println(Arrays.deepToString(arr));
    //System.out.println(Arrays.deepToString(expected));
    assertTrue(isEqual(expected, arr));
  }




}