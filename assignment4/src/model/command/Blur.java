package model.command;


import model.Cache;
import utils.arguments.ArgumentWrapper;

/**
 * class for the blur command. it extends filter as it is a filter operation.
 */
class Blur extends Filter {

  /**
   * constructor for the blur class. calls the filters constructor with the required arguments. and
   * the blur kernel.
   *
   * @param rawArguments takes the required string arguments.
   * @param cache        cache object.
   */
  public Blur(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, new double[][]{
        {
            1 / 16.0,
            2 / 16.0,
            1 / 16.0},
        {
            2 / 16.0,
            4 / 16.0,
            2 / 16.0},
        {
            1 / 16.0,
            2 / 16.0,
            1 / 16.0}}, cache);
  }
}
