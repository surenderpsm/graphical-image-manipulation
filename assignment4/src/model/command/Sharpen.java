package model.command;


import model.Cache;

/**
 * class for the sharpen command. it extends filter as it is a filter operation.
 */
class Sharpen extends Filter {

  /**
   * constructor for the sharpen class. calls the filters constructor with the required arguments.
   * and the sharpen kernel.
   *
   * @param rawArguments takes the required string arguments.
   * @param cache        cache object.
   */
  public Sharpen(String rawArguments, Cache cache) {
    super(rawArguments, new double[][]{
        {
            -0.125,
            -0.125,
            -0.125,
            -0.125,
            -0.125},
        {
            -0.125,
            0.25,
            0.25,
            0.25,
            -0.125},
        {
            -0.125,
            0.25,
            1.0,
            0.25,
            -0.125},
        {
            -0.125,
            0.25,
            0.25,
            0.25,
            -0.125},
        {
            -0.125,
            -0.125,
            -0.125,
            -0.125,
            -0.125}}, cache);
  }
}
