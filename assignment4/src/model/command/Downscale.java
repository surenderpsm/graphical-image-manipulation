package model.command;

import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;

/**
 * class to downscale image into the required dimensions.
 */
public class Downscale extends AbstractCommand {

  private int tHeight;
  private int tWidth;
  private final int height;
  private final int width;
  int[][][] imageArray;

  /**
   * Constructs a new Filter with the specified filter kernel.
   *
   * @param rawArguments Space-separated string of command arguments
   * @param cache        The cache containing stored images
   * @throws IllegalArgumentException if the number of arguments is not exactly 2
   */

  protected Downscale(ArgumentWrapper rawArguments, Cache cache) {
    super(rawArguments, cache);
    if (numberOfArgs() != 4) {
      throw new IllegalArgumentException("Expected 4 arguments.");
    }
    tWidth = parseInt(0);
    tHeight = parseInt(1);
    currentImage = cache.get(parseString(2));
    imageName = parseString(3);
    this.height = currentImage.getHeight();
    this.width = currentImage.getWidth();
    if (tHeight > height || tHeight < 1 || tWidth > width || tWidth < 1) {
      throw new IllegalArgumentException("Invalid input dimensions!");
    }
    imageArray = new int[tHeight][tWidth][currentImage.getNoOfChannels()];
  }

  /**
   * Processes the image by downscaling it to the new width and heights.
   */
  @Override
  public void execute() {
    double newRed = 0;
    double newGreen = 0;
    double newBlue = 0;

    for (int i = 0; i < tHeight; i++) { // y' coordinate
      for (int j = 0; j < tWidth; j++) { // x' coordinate

        double iOrigin = ((float) i / tHeight) * height;
        double jOrigin = ((float) j / tWidth) * width;

        if ((Math.floor(iOrigin) == iOrigin && Math.ceil(iOrigin) == iOrigin) && (
            Math.floor(jOrigin) == jOrigin && Math.ceil(jOrigin) == jOrigin)) {
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
          int ceilIOrigin = (int) Math.ceil(iOrigin);
          int ceilJOrigin = (int) Math.ceil(jOrigin);
          if (Math.ceil(iOrigin) == iOrigin && Math.ceil(jOrigin) != jOrigin) {
            ceilIOrigin = (int) Math.ceil(iOrigin) + 1;
            ceilJOrigin = (int) Math.ceil(jOrigin);
          }
          else if (Math.ceil(jOrigin) == jOrigin && Math.ceil(iOrigin) != iOrigin) {
            ceilIOrigin = (int) Math.ceil(iOrigin);
            ceilJOrigin = (int) Math.ceil(jOrigin) + 1;
          }
          else if (Math.ceil(jOrigin) == jOrigin && Math.ceil(iOrigin) == iOrigin) {
            ceilIOrigin = (int) Math.ceil(iOrigin) + 1;
            ceilJOrigin = (int) Math.ceil(jOrigin) + 1;
          }
          int
              aRed =
              currentImage.getRedChannelData()[(int) Math.floor(iOrigin)][(int) Math.floor(jOrigin)];
          int bRed = currentImage.getRedChannelData()[(int) Math.floor(iOrigin)][ceilJOrigin];
          int cRed = currentImage.getRedChannelData()[ceilIOrigin][(int) Math.floor(jOrigin)];
          int dRed = currentImage.getRedChannelData()[ceilIOrigin][ceilJOrigin];

          double mRed = bRed * (jOrigin - Math.floor(jOrigin)) + aRed * (ceilJOrigin - jOrigin);
          double nRed = dRed * (jOrigin - Math.floor(jOrigin)) + cRed * (ceilJOrigin - jOrigin);
          int
              pRed =
              (int) (nRed * (iOrigin - Math.floor(iOrigin)) + mRed * (ceilIOrigin - iOrigin));

          int
              aGreen =
              currentImage.getGreenChannelData()[(int) Math.floor(iOrigin)][(int) Math.floor(jOrigin)];
          int bGreen = currentImage.getGreenChannelData()[(int) Math.floor(iOrigin)][ceilJOrigin];
          int cGreen = currentImage.getGreenChannelData()[ceilIOrigin][(int) Math.floor(jOrigin)];
          int dGreen = currentImage.getGreenChannelData()[ceilIOrigin][ceilJOrigin];
          double
              mGreen =
              bGreen * (jOrigin - Math.floor(jOrigin)) + aGreen * (ceilJOrigin - jOrigin);
          double
              nGreen =
              dGreen * (jOrigin - Math.floor(jOrigin)) + cGreen * (ceilJOrigin - jOrigin);
          int
              pGreen =
              (int) (nGreen * (iOrigin - Math.floor(iOrigin)) + mGreen * (ceilIOrigin - iOrigin));

          int
              aBlue =
              currentImage.getBlueChannelData()[(int) Math.floor(iOrigin)][(int) Math.floor(jOrigin)];
          int bBlue = currentImage.getBlueChannelData()[(int) Math.floor(iOrigin)][ceilJOrigin];
          int cBlue = currentImage.getBlueChannelData()[ceilIOrigin][(int) Math.floor(jOrigin)];
          int dBlue = currentImage.getBlueChannelData()[ceilIOrigin][ceilJOrigin];
          double mBlue = bBlue * (jOrigin - Math.floor(jOrigin)) + aBlue * (ceilJOrigin - jOrigin);
          double nBlue = dBlue * (jOrigin - Math.floor(jOrigin)) + cBlue * (ceilJOrigin - jOrigin);
          int
              pBlue =
              (int) (nBlue * (iOrigin - Math.floor(iOrigin)) + mBlue * (ceilIOrigin - iOrigin));

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
