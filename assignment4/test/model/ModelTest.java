package model;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class ModelTest {

  int[][][] originalImage = get3DArrayFromFile("test/model/img/original.txt");
  String root = "test/model/img/";
  int[][][] brighten = get3DArrayFromFile(root + "brighten.txt");
  int[][][] redComponent = get3DArrayFromFile(root + "redComponent.txt");
  int[][][] greenComponent = get3DArrayFromFile(root + "greenComponent.txt");
  int[][][] blueComponent = get3DArrayFromFile(root + "blueComponent.txt");
  int[][][] horizontalFlip = get3DArrayFromFile(root + "horizontalFlip.txt");
  int[][][] verticalFlip = get3DArrayFromFile(root + "verticalFlip.txt");
  int[][][] blurred = get3DArrayFromFile(root + "blurred.txt");
  int[][][] sharpen = get3DArrayFromFile(root + "sharpen.txt");
  int[][][] luma = get3DArrayFromFile(root + "luma.txt");
  int[][][] value = get3DArrayFromFile(root + "value.txt");
  int[][][] intensity = get3DArrayFromFile(root + "intensity.txt");
  int[][][] sepia = get3DArrayFromFile(root + "sepia.txt");

  @Test
  public void brightenTest() {
    assertTrue(isEqual(brighten, executor("brighten")));
  }

  @Test
  public void RedComponentTest() {
    assertTrue(isEqual(redComponent, executor("redComponent")));
  }

  @Test
  public void greenComponentTest() {
    assertTrue(isEqual(greenComponent, executor("greenComponent")));
  }

  @Test
  public void blueComponentTest() {
    assertTrue(isEqual(blueComponent, executor("blueComponent")));
  }

  @Test
  public void horizontalFlipTest() {
    assertTrue(isEqual(horizontalFlip, executor("horizontalFlip")));
  }

  @Test
  public void verticalFlipTest() {
    assertTrue(isEqual(verticalFlip, executor("verticalFlip")));
  }

  @Test
  public void blurredTest() {
    assertTrue(isEqual(blurred, executor("blurred")));
  }

  @Test
  public void sharpenTest() {
    assertTrue(isEqual(sharpen, executor("sharpen")));
  }

  @Test
  public void lumaTest() {
    assertTrue(isEqual(luma, executor("luma")));
  }

  @Test
  public void valueTest() {
    assertTrue(isEqual(value, executor("value")));
  }

  @Test
  public void intensityTest() {
    assertTrue(isEqual(intensity, executor("intensity")));
  }

  @Test
  public void sepiaTest() {
    assertTrue(isEqual(sepia, executor("sepia")));
  }

  private int[][][] executor(String command){
    Model model = new Model();
    model.setImage(originalImage, "image");
    model.execute(command, "image "+command);
    return model.getImage(command);
  }

  private int[][][] get3DArrayFromFile(String path) {
    try {
      // Path to your text file
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
          String[] tokens = line.split("\\s+"); // Split by any whitespace
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

      // Convert the ArrayList of 2D arrays to a 3D array
      return arrayList.toArray(new int[0][][]);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean isEqual(int[][][] array1, int[][][] array2) {
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
