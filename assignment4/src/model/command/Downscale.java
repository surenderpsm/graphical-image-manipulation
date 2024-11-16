package model.command;

import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;

/**
 * class to downscale image into the required dimensions.
 */
public class Downscale extends ImageProcessor{
  private int tHeight;
  private int tWidth;

  /**
   * Constructs a new Filter with the specified filter kernel.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param tHeight      target image height
   * @param tWidth       target image width
   * @param cache        The cache containing stored images
   * @throws IllegalArgumentException if the number of arguments is not exactly 2
   */

  protected Downscale(ArgumentWrapper rawArguments, int tHeight, int tWidth, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 2 arguments.");
    }
    tWidth = parseInt(0);
    tHeight = parseInt(1);
    currentImage = cache.get(parseString(2));
    imageName = parseString(3);
    this.height = currentImage.getHeight();
    this.width = currentImage.getWidth();
  }

  /**
   * Processes the image by downscaling it to the new width and heights.
   */
  @Override
  protected void processImage() {
    double newRed = 0;
    double newGreen = 0;
    double newBlue = 0;

    for (int i = 0; i < tHeight; i++) { // y' coordinate
      for (int j = 0; j < tWidth; j++) { // x' coordinate

        double iOrigin = ( (float) i / tHeight ) * height;
        double jOrigin = ( (float) j / tWidth ) * width;

        if((Math.floor(iOrigin) == iOrigin && Math.ceil(iOrigin) == iOrigin)
                && (Math.floor(jOrigin) == jOrigin && Math.ceil(jOrigin) == jOrigin)){
          newRed = currentImage.getRedChannelData()[(int) iOrigin][(int) jOrigin];
          newGreen = currentImage.getGreenChannelData()[(int) iOrigin][(int) jOrigin];
          newBlue = currentImage.getBlueChannelData()[(int) iOrigin][(int) jOrigin];
          imageArray[i][j][0] = (int) newRed;
          imageArray[i][j][1] = (int) newGreen;
          imageArray[i][j][2] = (int) newBlue;

        }
        // rows => height => i => y
        // cols => width => j => x
        else {
          int aRed = currentImage.getRedChannelData()[(int) Math.floor(iOrigin)]
                  [(int) Math.floor(jOrigin)];
          int bRed = currentImage.getRedChannelData()[(int) Math.floor(iOrigin)]
                  [(int) Math.ceil(jOrigin)];
          int cRed = currentImage.getRedChannelData()[(int) Math.ceil(iOrigin)]
                  [(int) Math.floor(jOrigin)];
          int dRed = currentImage.getRedChannelData()[(int) Math.ceil(iOrigin)]
                  [(int) Math.ceil(jOrigin)];
          double mRed = bRed * (jOrigin - Math.floor(jOrigin))
                  + aRed * (Math.ceil(jOrigin) - jOrigin );
          double nRed = dRed * (jOrigin - Math.floor(jOrigin))
                  + cRed * (Math.ceil(jOrigin) - jOrigin );
          int pRed = (int) (nRed * (iOrigin - Math.floor(iOrigin))
                  + mRed * (Math.ceil(jOrigin) - jOrigin ));

          int aGreen = currentImage.getGreenChannelData()[(int) Math.floor(iOrigin)]
                  [(int) Math.floor(jOrigin)];
          int bGreen = currentImage.getGreenChannelData()[(int) Math.floor(iOrigin)]
                  [(int) Math.ceil(jOrigin)];
          int cGreen = currentImage.getGreenChannelData()[(int) Math.ceil(iOrigin)]
                  [(int) Math.floor(jOrigin)];
          int dGreen = currentImage.getGreenChannelData()[(int) Math.ceil(iOrigin)]
                  [(int) Math.ceil(jOrigin)];
          double mGreen = bGreen * (jOrigin - Math.floor(jOrigin))
                  + aGreen * (Math.ceil(jOrigin) - jOrigin );
          double nGreen = dGreen * (jOrigin - Math.floor(jOrigin))
                  + cGreen * (Math.ceil(jOrigin) - jOrigin );
          int pGreen = (int) (nGreen * (iOrigin - Math.floor(iOrigin))
                  + mGreen * (Math.ceil(jOrigin) - jOrigin ));

          int aBlue = currentImage.getBlueChannelData()[(int) Math.floor(iOrigin)]
                  [(int) Math.floor(jOrigin)];
          int bBlue = currentImage.getBlueChannelData()[(int) Math.floor(iOrigin)]
                  [(int) Math.ceil(jOrigin)];
          int cBlue = currentImage.getBlueChannelData()[(int) Math.ceil(iOrigin)]
                  [(int) Math.floor(jOrigin)];
          int dBlue = currentImage.getBlueChannelData()[(int) Math.ceil(iOrigin)]
                  [(int) Math.ceil(jOrigin)];
          double mBlue = bBlue * (jOrigin - Math.floor(jOrigin))
                  + aBlue * (Math.ceil(jOrigin) - jOrigin );
          double nBlue = dBlue * (jOrigin - Math.floor(jOrigin))
                  + cBlue * (Math.ceil(jOrigin) - jOrigin );
          int pBlue = (int) (nBlue * (iOrigin - Math.floor(iOrigin))
                  + mBlue * (Math.ceil(jOrigin) - jOrigin ));

          imageArray[i][j][0] = pRed;
          imageArray[i][j][1] = pGreen;
          imageArray[i][j][2] = pBlue;

        }

      }
    }

    Image processedImage = new Image(imageArray);
    cache.set(imageName, processedImage);
  }

}
