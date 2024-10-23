package model.img4x4;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import model.Model;
import org.junit.Test;

public class ModelTest4x4 {
  String root = "test/model/img4x4/";

  int[][][] originalImage = get3DArrayFromFile(root+"original.txt");

  int[][][] brighten = get3DArrayFromFile(root + "brighten.txt");


  @Test
  public void brightenTest() {

    assertTrue(isEqual(brighten, executor("brighten")));
  }

  public void darkenTest(){
      int[][][] darken = get3DArrayFromFile(root + "darken.txt");

    assertTrue(isEqual(darken, executor("darken")));
  }

  @Test
  public void RedComponentTest() {
    int[][][] redComponent = get3DArrayFromFile(root + "redComponent.txt");

    assertTrue(isEqual(redComponent, executor("redComponent")));
  }

  @Test
  public void greenComponentTest() {
    int[][][] greenComponent = get3DArrayFromFile(root + "greenComponent.txt");

    assertTrue(isEqual(greenComponent, executor("greenComponent")));
  }

  @Test
  public void blueComponentTest() {
    int[][][] blueComponent = get3DArrayFromFile(root + "blueComponent.txt");

    assertTrue(isEqual(blueComponent, executor("blueComponent")));
  }

  @Test
  public void horizontalFlipTest() {
    int[][][] horizontalFlip = get3DArrayFromFile(root + "horizontalFlip.txt");

    assertTrue(isEqual(horizontalFlip, executor("horizontalFlip")));
  }

  @Test
  public void verticalFlipTest() {
    int[][][] verticalFlip = get3DArrayFromFile(root + "verticalFlip.txt");

    assertTrue(isEqual(verticalFlip, executor("verticalFlip")));
  }

  @Test
  public void blurredTest() {
    int[][][] blurred = get3DArrayFromFile(root + "blurred.txt");

    assertTrue(isEqual(blurred, executor("blurred")));
  }

  @Test
  public void sharpenTest() {
    int[][][] sharpen = get3DArrayFromFile(root + "sharpen.txt");

    assertTrue(isEqual(sharpen, executor("sharpen")));
  }

  @Test
  public void lumaTest() {
    int[][][] luma = get3DArrayFromFile(root + "luma.txt");

    assertTrue(isEqual(luma, executor("luma")));
  }

  @Test
  public void valueTest() {
    int[][][] value = get3DArrayFromFile(root + "value.txt");

    assertTrue(isEqual(value, executor("value")));
  }

  @Test
  public void intensityTest() {
    int[][][] intensity = get3DArrayFromFile(root + "intensity.txt");
    assertTrue(isEqual(intensity, executor("intensity")));
  }

  @Test
  public void sepiaTest() {
    int[][][] sepia = get3DArrayFromFile(root + "sepia.txt");
    assertTrue(isEqual(sepia, executor("sepia")));
  }

  private int[][][] executor(String command) {
    Model model = new Model();
    model.setImage(originalImage, "image");
    if (Objects.equals(command, "brighten")) {
      model.execute(command, "10 image " + command);
    }
    else if (Objects.equals(command, "darken")) {
      model.execute(command, "-10 image " + command);
    }
    else{
      model.execute(command, "image " + command);
    }
    return model.getImage(command);
  }

  private int[][][] get3DArrayFromFile(String path) {
    try {
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
        } else {
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
