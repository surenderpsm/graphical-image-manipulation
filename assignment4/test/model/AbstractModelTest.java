package model;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractModelTest {


  protected abstract String getRoot();

  protected int[][][] originalImage;
  protected Model model;

  @Before
  public void setup() {
    model = new Model();
    originalImage = get3DArrayFromFile(getRoot() + "original.txt");
    model.setImage(originalImage, "image");
  }

  @Test
  public void rgbCombineTest() {
    int[][][] red = get3DArrayFromFile(getRoot() + "red.txt");
    int[][][] green = get3DArrayFromFile(getRoot() + "green.txt");
    int[][][] blue = get3DArrayFromFile(getRoot() + "blue.txt");
    model.setImage(red, "red");
    model.setImage(green, "green");
    model.setImage(blue, "blue");
    model.execute("rgb-combine", "combined red green blue");
    assertTrue(isEqual(originalImage, model.getImage("combined")));
  }

  @Test
  public void rgbSplitTest() {
    int[][][] red = get3DArrayFromFile(getRoot() + "red.txt");
    int[][][] green = get3DArrayFromFile(getRoot() + "green.txt");
    int[][][] blue = get3DArrayFromFile(getRoot() + "blue.txt");
    model.execute("rgb-split", "original red green blue");
    assertTrue(isEqual(red, model.getImage("red")));
    assertTrue(isEqual(green, model.getImage("green")));
    assertTrue(isEqual(blue, model.getImage("blue")));
  }

  @Test
  public void brightenBy10Test() {
    int[][][] brighten = get3DArrayFromFile(getRoot() + "brighten.txt");
    model.execute("brighten", "10 image brighten");
    assertTrue(isEqual(brighten, model.getImage("brighten")));
  }

  @Test
  public void darkenBy10Test() {
    int[][][] darken = get3DArrayFromFile(getRoot() + "darken.txt");
    model.execute("brighten", "-10 image darkened");
    assertTrue(isEqual(darken, model.getImage("darkened")));
  }

  @Test
  public void RedComponentTest() {
    int[][][] redComponent = get3DArrayFromFile(getRoot() + "redComponent.txt");

    model.execute("red-component", "image redComponent");
    int[][][] expectedImage = model.getImage("redComponent");
    assertTrue(isEqual(redComponent, expectedImage));
  }

  @Test
  public void greenComponentTest() {
    int[][][] greenComponent = get3DArrayFromFile(getRoot() + "greenComponent.txt");

    model.execute("green-component", "image " + "greenComponent");
    int[][][] expectedImage = model.getImage("greenComponent");

    assertTrue(isEqual(greenComponent, expectedImage));
  }

  @Test
  public void blueComponentTest() {
    int[][][] blueComponent = get3DArrayFromFile(getRoot() + "blueComponent.txt");

    model.execute("blue-component", "image " + "blueComponent");
    int[][][] expectedImage = model.getImage("blueComponent");
    assertTrue(isEqual(blueComponent, expectedImage));

  }

  @Test
  public void horizontalFlipTest() {
    int[][][] horizontalFlip = get3DArrayFromFile(getRoot() + "horizontalFlip.txt");

    model.execute("horizontal-flip", "image " + "horizontalFlip");
    int[][][] expectedImage = model.getImage("horizontalFlip");
    assertTrue(isEqual(horizontalFlip, expectedImage));
  }

  @Test
  public void verticalFlipTest() {
    int[][][] verticalFlip = get3DArrayFromFile(getRoot() + "verticalFlip.txt");

    model.execute("vertical-flip", "image " + "verticalFlip");
    int[][][] expectedImage = model.getImage("verticalFlip");
    assertTrue(isEqual(verticalFlip, expectedImage));
  }

  @Test
  public void blurredTest() {
    int[][][] blurred = get3DArrayFromFile(getRoot() + "blurred.txt");
    model.execute("blur", "image blurred");
    assertTrue(isEqual(blurred, model.getImage("blurred")));
  }

  @Test
  public void sharpenTest() {
    int[][][] sharpen = get3DArrayFromFile(getRoot() + "sharpen.txt");
    model.execute("sharpen", "image sharpen");
    assertTrue(isEqual(sharpen, model.getImage("sharpen")));
  }

  @Test
  public void lumaTest() {
    int[][][] luma = get3DArrayFromFile(getRoot() + "luma.txt");
    model.execute("luma", "image luma");
    assertTrue(isEqual(luma, model.getImage("luma")));
  }

  @Test
  public void valueTest() {
    int[][][] value = get3DArrayFromFile(getRoot() + "value.txt");
    model.execute("value", "image value");
    assertTrue(isEqual(value, model.getImage("value")));
  }

  @Test
  public void intensityTest() {
    int[][][] intensity = get3DArrayFromFile(getRoot() + "intensity.txt");
    model.execute("intensity", "image intensity");
    assertTrue(isEqual(intensity, model.getImage("intensity")));
  }

  @Test
  public void sepiaTest() {
    int[][][] sepia = get3DArrayFromFile(getRoot() + "sepia.txt");
    model.execute("sepia", "image sepia");
    assertTrue(isEqual(sepia, model.getImage("sepia")));
  }

  protected int[][][] get3DArrayFromFile(String path) {
    try {
//      System.out.println("Current working directory: " + System.getProperty("user.dir"));
      File file = new File(path);
      BufferedReader reader = new BufferedReader(new FileReader(file));

      // List to store 2D arrays
      ArrayList<int[][]> arrayList = new ArrayList<>();

      // Temporary storage for a 2D array
      ArrayList<int[]> temp2DArray = new ArrayList<>();

      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          // End of a 2D block
          if (!temp2DArray.isEmpty()) {
            // Convert temp 2D list to an array and add to the 3D array list
            arrayList.add(temp2DArray.toArray(new int[0][]));
            temp2DArray.clear();  // Clear for the next 2D array
          }
        }
        else {
          // Parse the line and add it as a row in the temp 2D array
          String[] tokens = line.split("\\s+"); // Split by whitespace
          int[] row = new int[tokens.length];
          for (int i = 0; i < tokens.length; i++) {
            row[i] = Integer.parseInt(tokens[i]); // Parse each integer
          }
          temp2DArray.add(row);
        }
      }

      // Add the last 2D array if any
      if (!temp2DArray.isEmpty()) {
        arrayList.add(temp2DArray.toArray(new int[0][]));
      }

      // Determine dimensions for the 3D array
      int width = arrayList.get(0).length; // Assuming all 2D arrays have the same width
      int height = arrayList.get(0)[0].length; // Assuming all 2D arrays have the same height
      int channels = arrayList.size();

      // Initialize the 3D array
      int[][][] image3D = new int[width][height][channels];

      // Fill the 3D array
      for (int channel = 0; channel < channels; channel++) {
        int[][] current2DArray = arrayList.get(channel);
        for (int w = 0; w < current2DArray.length; w++) {
          for (int h = 0; h < current2DArray[w].length; h++) {
            image3D[w][h][channel] = current2DArray[w][h];
          }
        }
      }

      return image3D; // Return the constructed 3D array
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  protected boolean isEqual(int[][][] array1, int[][][] array2) {
    // Check if both arrays are the same size
    if (array1.length != array2.length) {
      return false;
    }

    // Compare elements in each dimension
    for (int i = 0; i < array1.length; i++) {
      if (array1[i].length != array2[i].length) {
        return false;
      }

      for (int j = 0; j < array1[i].length; j++) {
        if (array1[i][j].length != array2[i][j].length) {
          return false;
        }

        // Compare individual elements
        if (!Arrays.equals(array1[i][j], array2[i][j])) {
          return false;
        }
      }
    }
    // All elements are equal
    return true;
  }
}
