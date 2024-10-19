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

  /**
   * This constructor is used to instantiate an "empty" Image object.
   * @todo IDK if this is needed anymore.
   */
  public Image() {
    isEmpty = true;
  }

  public Image(int[][][] image) {
    int height = image.length;
    int width = image[0].length;
    int num_channels = image[0][0].length;
    // @todo Complete implementation here. int[][][] has [width][height][num_channels]
  }

  /**
   * Checks if the Image is empty.
   *
   * @return
   */
  public boolean isEmpty() {
    return isEmpty;
  }


  public int[][][] getImageArray() {
    throw new UnsupportedOperationException("Not supported yet.");
    // @todo Need to convert to 3d array [width][height][num_channels]
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
