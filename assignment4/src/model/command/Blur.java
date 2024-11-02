package model.command;


import model.Cache;

class Blur extends Filter {

  public Blur(String rawArguments, Cache cache) {
    super(rawArguments,
        new double[][]{{1 / 16.0, 2 / 16.0, 1 / 16.0}, {2 / 16.0, 4 / 16.0, 2 / 16.0},
            {1 / 16.0, 2 / 16.0, 1 / 16.0}}, cache);
  }
}
