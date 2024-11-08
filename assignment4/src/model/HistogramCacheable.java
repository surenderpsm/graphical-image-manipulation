package model;

import java.util.NoSuchElementException;

/**
 * provider for histogram cache. it has all the methods required by. any cache that stores
 * histograms.
 */

public interface HistogramCacheable {

  /**
   * to check if the histogram object is present in the cache.
   *
   * @param name of the histogram object.
   * @return boolean true or false based on presence.
   */

  boolean isHistogram(String name);

  /**
   * get the required histogram object from the cache.
   *
   * @param name of the histogram object.
   * @return the histogram object.
   * @throws NoSuchElementException if the object is not found in the cache.
   */

  Histogram getHistogram(String name) throws NoSuchElementException;

  /**
   * function polymorphism. this set method takes a histogram object to put in the cache.
   *
   * @param name      of the histogram object.
   * @param histogram histogram object.
   */

  void set(String name, Histogram histogram);
}
