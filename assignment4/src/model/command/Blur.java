package model.command;


class Blur extends Filter {

  public Blur(String rawArguments) {
    super(rawArguments,
        new double[][]{{1 / 16.0, 2 / 16.0, 1 / 16.0}, {2 / 16.0, 4 / 16.0, 2 / 16.0},
            {1 / 16.0, 2 / 16.0, 1 / 16.0}});
  }
}
