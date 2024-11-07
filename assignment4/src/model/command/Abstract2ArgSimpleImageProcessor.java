package model.command;

import model.Cache;
import model.Image;

abstract class Abstract2ArgSimpleImageProcessor extends SimpleImageProcessor {

  protected Abstract2ArgSimpleImageProcessor(String rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(getArg(0));
    imageName = getArg(1);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
  }

  protected Abstract2ArgSimpleImageProcessor(String rawArguments,
                                             Cache cache,
                                             PixelTransformer transformer) {
    super(rawArguments, cache);
    if (numberOfArgs() != 2) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    currentImage = cache.get(getArg(0));
    imageName = getArg(1);
    height = currentImage.getHeight();
    width = currentImage.getWidth();
    setTransformer(transformer);
  }
}
