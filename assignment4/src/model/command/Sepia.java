package model.command;


import model.Cache;

class Sepia extends ColorTransform {

  public Sepia(String rawArguments, Cache cache) {
    super(rawArguments,
        new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}}, cache);
  }

}
