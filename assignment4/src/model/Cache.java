package model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class represents a temporary storage medium for a collection of {@code model.Image}. A
 * {@code java.util.HashMap} is used to store the {@code model.Image} and is identified by a unique
 * name.
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
public class Cache implements HistogramCacheable, ImageCacheable {

  /**
   * hashmap to store cached images and histograms.
   */
  private final Map<String, Object> cache = new HashMap<>();

  /**
   * get method to get an image object from cache.
   *
   * @param name A {@code String} which denotes the image name.
   * @return image object.
   * @throws NoSuchElementException when no such image object in the cache
   */

  @Override
  public Image get(String name) throws NoSuchElementException {
    Image image = (Image) cache.get(name);
    if (!(cache.get(name) instanceof Image)) {
      throw new NoSuchElementException("Image " + name + " not found in cache.");
    }
    return image;
  }

  /**
   * set an image object to the cache.
   *
   * @param name  A {@code String} which denotes the image name.
   * @param image A {@code model.Image} object to map to {@code name}.
   */
  @Override
  public void set(String name, Image image) {
    cache.put(name, image);
  }

  /**
   * function polymorphism. this set method takes a histogram object to put in the cache.
   *
   * @param name      of the histogram object.
   * @param histogram histogram object.
   */
  @Override
  public void set(String name, Histogram histogram) {
    cache.put(name, histogram);
  }

  /**
   * to check if the histogram object is present in the cache.
   *
   * @param name of the histogram object.
   * @return boolean true or false based on presence.
   */
  @Override
  public boolean isHistogram(String name) {
    return cache.get(name) instanceof Histogram;
  }

  /**
   * get the required histogram object from the cache.
   *
   * @param name of the histogram object.
   * @return the histogram object.
   * @throws NoSuchElementException if the object is not found in the cache.
   */
  @Override
  public Histogram getHistogram(String name) throws NoSuchElementException {
    Histogram histogram = (Histogram) cache.get(name);
    if (histogram == null || !(cache.get(name) instanceof Histogram)) {
      throw new NoSuchElementException("Histogram " + name + " not found in cache.");
    }
    return histogram;
  }
}
