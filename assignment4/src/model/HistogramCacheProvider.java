package model;

import java.util.NoSuchElementException;

/**
 * provider for histogram cache to model. it has all the methods required by.
 * any cache that stores histograms.
 */

public interface HistogramCacheProvider {

  /**
   * Checks if there exists a histogram for the given name.
   *
   * @param name of the histogram.
   * @return true if it exists.
   */
  boolean isHistogram(String name);

  /**
   * Retrieves the histogram data for a specified name from the cache. If the image is not found, a
   * {@link NoSuchElementException} is thrown.
   *
   * <p>
   * The histogram data is returned as a 2D array formatted as {@code int[num_channels][bins]}:
   * <ul>
   *   <li>num_channels: the number of color channels (e.g., RGB, RGBA) default 3</li>
   *   <li>bins: the number of bins default 256</li>*
   * </ul>
   * </p>
   *
   * @param name the name of the histogram to retrieve
   * @return a 2D array representing the histogram data
   * @throws NoSuchElementException if the specified histogram is not found in the cache
   */
  int[][] getHistogram(String name) throws NoSuchElementException;

}
