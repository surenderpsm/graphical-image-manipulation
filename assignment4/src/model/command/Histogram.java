package model.command;

import model.Cache;

/**
 * Generates a histogram representation of an image's color distribution. This command creates a
 * histogram object that represents the frequency distribution of pixel values across all color
 * channels (red, green, and blue) in the source image. The histogram can be used for analysis of
 * the image's tonal distribution and as input for other image processing operations like color
 * correction or level adjustment.
 */
class Histogram extends Abstract2ArgCommand {

  /**
   * Constructs a new Histogram processor.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */

  public Histogram(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  /**
   * Executes the histogram generation by creating a new Histogram object from the source image and
   * storing it in the cache. The resulting histogram contains frequency counts for each possible
   * pixel value (0-255) for each color channel.
   */
  @Override
  public void execute() {
    cache.set(imageName, new model.Histogram(currentImage));
  }
}
