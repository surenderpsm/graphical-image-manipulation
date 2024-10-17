package model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class ImageCache {

  private static final Map<String, Image> cache = new HashMap<>();

  public static Image getImage(String name) {
    Image image = cache.get(name);
    if (image == null) {
      throw new NoSuchElementException("Image not found");
    }
    return cache.get(name);
  }

  public static void setImage(String name, Image image) {
    cache.put(name, image);
  }

  public static void removeImage(String name) {
    if (!cache.containsKey(name)) {
      throw new NoSuchElementException("Image not found");
    }
    cache.remove(name);
  }

}
