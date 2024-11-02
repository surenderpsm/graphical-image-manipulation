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

  private final Map<String, Object> cache = new HashMap<>();

  @Override
  public Image get(String name) throws NoSuchElementException {
    Image image = (Image) cache.get(name);
    if (cache.get(name) == null || !(cache.get(name) instanceof Image)) {
      throw new NoSuchElementException("Image " + name + " not found in cache.");
    }
    return image;
  }

  @Override
  public void set(String name, Image image) {
    cache.put(name, image);
  }

  @Override
  public boolean isHistogram(String name) {
    return cache.get(name) instanceof Histogram;
  }

  @Override
  public Histogram getHistogram(String name) throws NoSuchElementException {
    Histogram histogram = (Histogram) cache.get(name);
    if (histogram == null || !(cache.get(name) instanceof Histogram)) {
      throw new NoSuchElementException("Histogram " + name + " not found in cache.");
    }
    return histogram;
  }

  @Override
  public void set(String name, Histogram histogram) {
    cache.put(name, histogram);
  }

}
