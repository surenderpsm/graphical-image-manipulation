package model.command;


import model.Cache;

class Sharpen extends Filter {

  public Sharpen(String rawArguments, Cache cache) {
    super(rawArguments,
        new double[][]{{-0.125, -0.125, -0.125, -0.125, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, 0.25, 1.0, 0.25, -0.125}, {-0.125, 0.25, 0.25, 0.25, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}}, cache);
  }
}
