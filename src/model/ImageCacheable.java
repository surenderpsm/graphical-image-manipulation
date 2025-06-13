package model;

import java.util.NoSuchElementException;

/**
 * provider for image cache. it has all the methods required by. any cache that stores images.
 */

public interface ImageCacheable {

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
  Image get(String name) throws NoSuchElementException;


  /**
   * This method maps a {@code String} key to a {@code model.Image} value. If the item exists, it is
   * overwritten.
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
  void set(String name, Image image);

}
