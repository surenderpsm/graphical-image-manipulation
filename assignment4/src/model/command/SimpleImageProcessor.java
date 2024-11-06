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

  protected SimpleImageProcessor(Image image, String imageName, Cache cache, PixelTransformer transformer) {
    super(image, imageName, cache);
    this.transformer = transformer;
  }

  protected void setTransformer(PixelTransformer transformer) {
    this.transformer = transformer;
  }

  @Override
  protected void processImage() {
    if (transformer == null) throw new IllegalStateException("Internal error: No transformer set");
    int[][][] imageArray = new int[height][width][3];
    int[][] redChannel = currentImage.getRedChannelData();
    int[][] greenChannel = currentImage.getGreenChannelData();
    int[][] blueChannel = currentImage.getBlueChannelData();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
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
