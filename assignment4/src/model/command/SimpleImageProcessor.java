package model.command;

import model.Cache;
import model.Image;

abstract class SimpleImageProcessor extends ImageProcessor {

  private PixelTransformer transformer;

  protected SimpleImageProcessor(String rawArguments, Cache cache, PixelTransformer transformer) {
    super(rawArguments, cache);
    this.transformer = transformer;
  }

  protected SimpleImageProcessor(String rawArguments, Cache cache) {
    super(rawArguments, cache);
  }

  protected void setTransformer(PixelTransformer transformer) {
    this.transformer = transformer;
  }

  @Override
  protected void processImage() {
    if (height == 0 || width == 0) {
      throw new IllegalStateException("Internal error: height or width not set.");
    }
    if (transformer == null) {
      throw new IllegalStateException("Internal error: No transformer set");
    }

    int[][] redChannel = currentImage.getRedChannelData();
    int[][] greenChannel = currentImage.getGreenChannelData();
    int[][] blueChannel = currentImage.getBlueChannelData();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < workingWidth; j++) {
        int[] transformedPixel = transformer.transformPixel(redChannel[i][j],
                                                            greenChannel[i][j],
                                                            blueChannel[i][j]);
        imageArray[i][j] = transformedPixel;
      }
    }

    Image processedImage = new Image(imageArray);
    cache.set(imageName, processedImage);
  }
}
