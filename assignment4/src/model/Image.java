package model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class represents an image. @todo documentation
 *
 * @see Image.Cache
 */
public class Image {

  // isEmpty tracks whether an Image is instantiated as an empty object or with parameters.
  private boolean isEmpty = false;


  // @todo add private final members as required.
  private int height;
  private int width;
  private int noOfChannels;
  private int[][] redChannelData;
  private int[][] greenChannelData;
  private int[][] blueChannelData;
  private int[][] transparency;

  /**
   * This constructor is used to instantiate an "empty" Image object.
   *
   * @todo IDK if this is needed anymore.
   */
  public Image() {
    isEmpty = true;
  }

  public Image(int[][][] image) {
    height = arr.length;
    width = arr[0].length;
    noOfChannels = arr[0][0].length;


    redChannelData = new int[height][width];
    greenChannelData = new int[height][width];
    blueChannelData = new int[height][width];
    value = new int[height][width];
    intensity = new int[height][width];
    luma = new int[height][width];

    if (noOfChannels == 4) {
      transparency = new int[height][height];
    }


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        redChannelData[i][j] = arr[i][j][0];
        if (noOfChannels > 1) {
          greenChannelData[i][j] = arr[i][j][1];
        }

        if (noOfChannels > 2) {
          blueChannelData[i][j] = arr[i][j][2];
        }

        if (noOfChannels == 4) {
          transparency[i][j] = arr[i][j][3];

        }
      }
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public int getNoOfChannels() {
    return noOfChannels;
  }

  /**
   * Checks if the Image is empty.
   *
   * @return
   */
  public boolean isEmpty() {
    return isEmpty;
  }

  public int[][] getRedChannelData() {
    return redChannelData;
  }

  public int[][] getGreenChannelData() {
    if (noOfChannels < 2) {
      return null;
    }
    return greenChannelData;
  }

  public int[][] getBlueChannelData() {
    if (noOfChannels < 3) {
      return null;
    }
    return blueChannelData;
  }

  public int[][] getTransparencyData() {
    if (noOfChannels < 4) {
      return null;
    }
    return transparency;
  }

  public int[] getPixelData(int i, int j) {
    if (noOfChannels == 3) {
      int[] arr = new int[3];
      arr[0] = redChannelData[i][j];
      arr[1] = greenChannelData[i][j];
      arr[2] = blueChannelData[i][j];
    } else if (noOfChannels == 4) {
      int[] arr = new int[4];
      arr[0] = redChannelData[i][j];
      arr[1] = greenChannelData[i][j];
      arr[2] = blueChannelData[i][j];
      arr[3] = transparency[i][j];
    }
  }

  public int[][][] getImageArray() {
    int imageArr[][][] = new int[this.height][this.width][this.noOfChannels];
    for (int i = 0; i < this.height; i++) {
      for (int h = 0; j < this.width; j++) {
        imageArr[i][j][0] = redChannelData[i][j];
      }
    }

    if (noOfChannels > 1) {
      for (int i = 0; i < this.height; i++) {
        for (int h = 0; j < this.width; j++) {
          imageArr[i][j][1] = greenChannelData[i][j];
        }
      }
    }

    if (noOfChannels > 2) {
      for (int i = 0; i < this.height; i++) {
        for (int h = 0; j < this.width; j++) {
          imageArr[i][j][2] = blueChannelData[i][j];
        }
      }
    }
    if (noOfChannels == 4) {
      for (int i = 0; i < this.height; i++) {
        for (int h = 0; j < this.width; j++) {
          imageArr[i][j][3] = transparency[i][j];
        }
      }
    }

    return imageArr;
  }


  /**
   * This class represents a temporary storage medium for a collection of {@code model.Image}. A
   * {@code java.util.HashMap} is used to store the {@code model.Image} and is identified by a
   * unique name.
   * <br>
   * This class provides basic operations such as accessing an image given a name, removing an image
   * given a name, and setting a new image/overwriting a name with a new image.
   * <br>
   * This class being static, cannot be instantiated.
   * <br>
   * Usage:
   * <pre>
   *   Image i = Image.Cache.get("koala");       // This method gets the image named koala.
   *   Image.Cache.set("koala", i);              // This method sets an image i to koala.
   *   Image.Cache.remove("koala")               // This method removes an image named koala.
   * </pre>
   *
   * @see Image
   */
  public static class Cache {

    private static final Map<String, Image> cache = new HashMap<>();

    /**
     * This method gets a {@code model.Image} from the {@code cache} if it exists.
     * <br>
     * <h3>Usage:</h3>
     * <pre>
     *    Image i = Image.Cache.get("koala");     // This method gets the image named koala.
     * </pre>
     *
     * @param name A {@code String} which denotes the image name.
     * @return a {@code model.Image} object which is the mapped image.
     * @throws NoSuchElementException If the name is not found in the hashMap.
     */
    public static Image get(String name) throws NoSuchElementException {
      Image image = cache.get(name);
      if (image == null) {
        throw new NoSuchElementException("Image not found");
      }
      return cache.get(name);
    }

    /**
     * This method maps a {@code String} key to a {@code model.Image} value. If the item exists, it
     * is overwritten.
     * <br>
     * This method doesn't check if the name already exists because it is a valid operation to
     * overwrite if the name already exists.
     * <br>
     * <h3>Usage</h3>
     * <pre>
     *   Image i = new Image(...);
     *   Image.Cache.set('koala', i);     // Setting a new image to koala.
     *   Image j = new Image(...);
     *   Image.Cache.set('koala', j);     // Overwriting the key koala with a new image.
     * </pre>
     *
     * @param name  A {@code String} which denotes the image name.
     * @param image A {@code model.Image} object to map to {@code name}.
     */
    public static void set(String name, Image image) {
      cache.put(name, image);
    }

    /**
     * This method removes a {@code model.Image} from the {@code cache} if it exists.
     * <h3>Usage:</h3>
     * <pre>
     *   Image.Cache.remove("koala")      // Removes koala if exists.
     * </pre>
     *
     * @param name A {@code String} which denotes the image name.
     * @throws NoSuchElementException If the name is not found in the {@code cache}.
     */
    public static void remove(String name) {
      if (!cache.containsKey(name)) {
        throw new NoSuchElementException("Image not found");
      }
      cache.remove(name);
    }
  }
}
