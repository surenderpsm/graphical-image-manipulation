package model.command;


import model.Image;

class Sharpen extends Filter {

  public Sharpen(String rawArguments) {
    super(rawArguments, new double[][]{
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125,  0.25,   0.25,   0.25,  -0.125},
        {-0.125,  0.25,   1.0,    0.25,  -0.125},
        {-0.125,  0.25,   0.25,   0.25,  -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}
    });
  }
}
