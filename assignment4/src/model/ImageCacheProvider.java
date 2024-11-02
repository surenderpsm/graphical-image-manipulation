package model;

import java.util.NoSuchElementException;

public interface ImageCacheProvider {

  /**
   * Retrieves the image data for a specified image name from the cache. If the image is not found,
   * a {@link NoSuchElementException} is thrown.
   *
   * <p>
   * The image data is returned as a 3D array formatted as
   * {@code int[width][height][num_channels]}:
   * <ul>
   *   <li>width: the width of the image</li>
   *   <li>height: the height of the image</li>
   *   <li>num_channels: the number of color channels (e.g., RGB, RGBA)</li>
   * </ul>
   * </p>
   *
   * @param name the name of the image to retrieve
   * @return a 3D array representing the image data
   * @throws NoSuchElementException if the specified image is not found in the cache
   */
  int[][][] getImage(String name) throws NoSuchElementException;

  /**
   * Stores an image in the cache with the specified name. This method updates the status flag to
   * {@code true} once the image is successfully set.
   *
   * @param image a 3D array representing the image data in the format
   *              {@code int[width][height][num_channels]}
   * @param name  the name under which the image will be stored
   */
  void setImage(int[][][] image, String name);
}
