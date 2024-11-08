package model.command;


import model.Cache;

/**
 * Applies a sepia tone effect to an image using a predefined color transformation matrix. The sepia
 * effect creates a warm, brownish tone commonly associated with vintage photographs.
 */

class Sepia extends ColorTransform {

  /**
   * Constructs a new Sepia processor with a fixed color transformation matrix.
   *
   * @param rawArguments The command arguments containing source and destination image names
   * @param cache        The cache storing the images
   * @throws IllegalArgumentException if the arguments are invalid or images cannot be found
   */

  public Sepia(String rawArguments, Cache cache) {
    super(rawArguments, new double[][]{
        {
            0.393,
            0.769,
            0.189},
        {
            0.349,
            0.686,
            0.168},
        {
            0.272,
            0.534,
            0.131}}, cache);
  }

}
