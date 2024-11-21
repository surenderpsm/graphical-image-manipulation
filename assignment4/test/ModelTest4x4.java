import org.junit.Test;

import java.util.Arrays;

import utils.arguments.ArgumentWrapper;
import utils.arguments.IntArgument;
import utils.arguments.OptionalArgumentKeyword;
import utils.arguments.StringArgument;

import static org.junit.Assert.assertTrue;

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
  /**
   * test for compression.
   */
  @Test
  public void compressionTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "compression.txt");
    model.execute("compress",
            new ArgumentWrapper(new IntArgument(60),
                    new StringArgument("image"),
                    new StringArgument("compressed")));
    int[][][] arr = model.getImage("compressed");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for invalid input compression.
   */
  @Test(expected = IllegalArgumentException.class)
  public void compressionTest2() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "compression.txt");
    model.execute("compress",
            new ArgumentWrapper(new IntArgument(-30),
                    new StringArgument("image"),
                    new StringArgument("compressed")));
  }

  /**
   * test for invalid input compression.
   */
  @Test(expected = IllegalArgumentException.class)
  public void compressionTest3() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "compression.txt");
    model.execute("compress",
            new ArgumentWrapper(new IntArgument(175),
                    new StringArgument("image"),
                    new StringArgument("compressed")));
  }


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
  @Test
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

  /**
   * Apply "brighten 50" on only first 50% of the image.
   */
  @Test
  public void splitOnBrightenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "brighten50split50.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(50),
            new StringArgument("image"),
            new StringArgument("brighten-by-50-split"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 50);
    model.execute("brighten", argumentWrapper);
    int[][][] arr = model.getImage("brighten-by-50-split");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply "luma" on 25% of image.
   */
  @Test
  public void splitOnLumaComponent() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "lumaSplit25.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(
            new StringArgument("image"),
            new StringArgument("luma-split-25"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 25);
    model.execute("luma-component", argumentWrapper);
    int[][][] arr = model.getImage("luma-split-25");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply "brighten -10" on 100% of image.
   */
  @Test
  public void splitOnDarkenTest() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "darken.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(-10),
            new StringArgument("image"),
            new StringArgument("darkened"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 100);
    model.execute("brighten", argumentWrapper);
    int[][][] arr = model.getImage("darkened");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply "color-correct" on 75% of image.
   */
  @Test
  public void splitOnColorCorrect() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "colorCorrectSplit75.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(
            new StringArgument("image"),
            new StringArgument("colorCorrected"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 75);
    model.execute("color-correct", argumentWrapper);
    int[][][] arr = model.getImage("colorCorrected");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply "levels-adjust" on 20% of the image.
   */
  @Test
  public void splitOnLevelAdjust() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "levelsAdjustSplit25.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(0),
            new IntArgument(128),
            new IntArgument(255),
            new StringArgument("image"),
            new StringArgument("levelAdjusted"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 20);
    model.execute("levels-adjust", argumentWrapper);
    int[][][] arr = model.getImage("levelAdjusted");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply "levels-adjust" invalid case.
   */
  @Test(expected = IllegalArgumentException.class)
  public void splitOnLevelAdjustInvalid() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "levelsAdjustSplit25.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(128),
            new IntArgument(255),
            new IntArgument(0),
            new StringArgument("image"),
            new StringArgument("levelAdjusted"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 20);
    model.execute("levels-adjust", argumentWrapper);
    int[][][] arr = model.getImage("levelAdjusted");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply "levels-adjust" invalid case.
   */
  @Test(expected = IllegalArgumentException.class)
  public void splitOnLevelAdjustInvalid2() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "levelsAdjustSplit25.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(258),
            new IntArgument(258),
            new IntArgument(-300),
            new StringArgument("image"),
            new StringArgument("levelAdjusted"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 20);
    model.execute("levels-adjust", argumentWrapper);
    int[][][] arr = model.getImage("levelAdjusted");
    assertTrue(isEqual(expected, arr));
  }



  /**
   * Apply "value-component" on 0% of the image.
   */
  @Test
  public void splitOnValueComponent() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "original.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(
            new StringArgument("image"),
            new StringArgument("valueComponent"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 0);
    model.execute("value-component", argumentWrapper);
    int[][][] arr = model.getImage("valueComponent");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply intensity-component on 90% of the image.
   */
  @Test
  public void splitOnIntensityComponent() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "IntensityComponentSplit75.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(
            new StringArgument("image"),
            new StringArgument("intensityComponent"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 90);
    model.execute("intensity-component", argumentWrapper);
    int[][][] arr = model.getImage("intensityComponent");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test invalid split < 0% of the image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void TestInvalidSplit() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "IntensityComponentSplit75.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(
            new StringArgument("image"),
            new StringArgument("intensityComponent"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, -5);
    model.execute("intensity-component", argumentWrapper);
    int[][][] arr = model.getImage("intensityComponent");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test invalid split > 100% of the image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void TestInvalidSplit2() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "IntensityComponentSplit75.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(
            new StringArgument("image"),
            new StringArgument("intensityComponent"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 125);
    model.execute("intensity-component", argumentWrapper);
    int[][][] arr = model.getImage("intensityComponent");
    assertTrue(isEqual(expected, arr));
  }
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
//    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
//            new StringArgument("histogram"));
//    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 10);
//    model.execute("histogram", argumentWrapper);
//  }

  /**
   * Apply blur on 25% of the image.
   */
  @Test
  public void splitOnBlur() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "BlurSplit25.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("blurSplit"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 25);
    model.execute("blur", argumentWrapper);
    int[][][] arr = model.getImage("blurSplit");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply blur on 50% of the image.
   */
  @Test
  public void splitOnSharpen() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "SharpenSplit50.txt");
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("sharpenSplit"));
    argumentWrapper.setArgument(OptionalArgumentKeyword.SPLIT, 50);
    model.execute("sharpen", argumentWrapper);
    int[][][] arr = model.getImage("sharpenSplit");
    assertTrue(isEqual(expected, arr));
  }

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
            new IntArgument(2), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(4),
            new IntArgument(5), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale2() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(0),
            new IntArgument(0), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale3() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(7),
            new IntArgument(14), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale4() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(0),
            new IntArgument(14), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale5() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(23),
            new IntArgument(0), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale6() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(-23),
            new IntArgument(3), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale7() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(3),
            new IntArgument(-42), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidDownscale8() {
    int[][][] expected = {{{}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(-37),
            new IntArgument(-42), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);
    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test
  public void TestDownscale2() {
    int[][][] expected = {{{199, 95, 90}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(1),
            new IntArgument(1), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test
  public void TestDownscale3() {
    int[][][] expected = {{{199, 95, 90}, {206, 115, 112}, {183, 101, 102}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(3),
            new IntArgument(1), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * Apply downscaling of the image.
   */
  @Test
  public void TestDownscale4() {
    int[][][] expected = {{{199, 95, 90}}, {{166, 80, 97}}};
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new IntArgument(1),
            new IntArgument(2), new StringArgument("image"),
            new StringArgument("downscale2x2"));
    model.execute("downscale", argumentWrapper);

    int[][][] arr = model.getImage("downscale2x2");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingBlur() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialBlur.txt");
    ;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("blurred"));
    model.execute("blur", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("blurred"),
            new StringArgument("PartialBlurred"));
    argumentWrapper2.setArgument(OptionalArgumentKeyword.MASKIMG, "imageMask");
    model.execute("partial-process", argumentWrapper2);
    int[][][] arr = model.getImage("PartialBlurred");
    System.out.println(Arrays.deepToString(arr));
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingSharpen() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialSharpen.txt");
    ;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Sharpened"));
    model.execute("sharpen", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Sharpened"),
            new StringArgument("PartialSharpen"));
    argumentWrapper2.setArgument(OptionalArgumentKeyword.MASKIMG, "imageMask");
    model.execute("partial-process", argumentWrapper2);
    int[][][] arr = model.getImage("PartialSharpen");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingSepia() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialSepia.txt");
    ;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Sepiafied"));
    model.execute("sepia", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Sepiafied"),
            new StringArgument("PartialSepia"));
    argumentWrapper2.setArgument(OptionalArgumentKeyword.MASKIMG, "imageMask");
    model.execute("partial-process", argumentWrapper2);
    int[][][] arr = model.getImage("PartialSepia");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingGrayscale() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialGreyscale.txt");
    ;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("grayscalefied"));
    model.execute("grayscale", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("grayscalefied"),
            new StringArgument("PartialGrayscale"));
    argumentWrapper2.setArgument(OptionalArgumentKeyword.MASKIMG, "imageMask");
    model.execute("partial-process", argumentWrapper2);
    int[][][] arr = model.getImage("PartialGrayscale");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingRed() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialRed.txt");
    ;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Red"));
    model.execute("red-component", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Red"),
            new StringArgument("PartialRed"));
    argumentWrapper2.setArgument(OptionalArgumentKeyword.MASKIMG, "imageMask");
    model.execute("partial-process", argumentWrapper2);
    int[][][] arr = model.getImage("PartialRed");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingGreen() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialGreen.txt");
    ;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Green"));
    model.execute("green-component", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Green"),
            new StringArgument("PartialGreen"));
    argumentWrapper2.setArgument(OptionalArgumentKeyword.MASKIMG, "imageMask");
    model.execute("partial-process", argumentWrapper2);
    int[][][] arr = model.getImage("PartialGreen");
    assertTrue(isEqual(expected, arr));
  }

  /**
   * test for partial image processing.
   */
  @Test
  public void TestPartialProcessingBlue() {
    int[][][] expected = get3DArrayFromFile(getRoot() + "partialBlue.txt");
    ;
    ArgumentWrapper argumentWrapper = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Blue"));
    model.execute("blue-component", argumentWrapper);
    ArgumentWrapper argumentWrapper2 = new ArgumentWrapper(new StringArgument("image"),
            new StringArgument("Blue"),
            new StringArgument("PartialBlue"));
    argumentWrapper2.setArgument(OptionalArgumentKeyword.MASKIMG, "imageMask");
    model.execute("partial-process", argumentWrapper2);
    int[][][] arr = model.getImage("PartialBlue");
    assertTrue(isEqual(expected, arr));
  }


}