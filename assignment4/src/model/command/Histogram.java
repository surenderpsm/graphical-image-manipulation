package model.command;

import model.Cache;

class Histogram extends Abstract2ArgCommand {

  public Histogram(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  @Override
  public void execute() {
    cache.set(imageName, new model.Histogram(currentImage));
  }
}
