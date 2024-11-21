package model.command;

import model.Cache;
import model.Image;
import utils.arguments.ArgumentWrapper;
import utils.arguments.OptionalArgumentKeyword;

/**
 * partial processor class used to partially process images.
 */
public class PartialProcessor extends AbstractCommand {

  protected Image filteredImage;
  protected Image maskImage;

  /**
   * constructor of the partial processor.
   *
   * @param rawArguments arguments required which contain what images to work on. what to store the
   *                     final image as.
   * @param cache        in which cache to retrieve and store from.
   */
  public PartialProcessor(ArgumentWrapper rawArguments, Cache cache) {
    // what this class needs.
    // 1. name of the current image we are working on (eg parrot, donut etc).
    // 2. name of processed image.
    // 2. name of mask image.
    // 3. dest-name to store.
    super(rawArguments, cache);
    currentImage = cache.get(parseString(0));
    filteredImage = cache.get(parseString(1));
    maskImage = cache.get(parseString(OptionalArgumentKeyword.MASKIMG));
    imageName = parseString(2);

  }

  /**
   * the execute method of the class.
   */
  @Override
  public void execute() {
    int[][][] imageArray = new int[maskImage.getHeight()]
        [maskImage.getWidth()]
        [maskImage.getNoOfChannels()];
    for (int i = 0; i < maskImage.getHeight(); i++) {
      for (int j = 0; j < maskImage.getWidth(); j++) {
        if (maskImage.getRedChannelData()[i][j] == 0 && maskImage.getGreenChannelData()[i][j] == 0
            && maskImage.getBlueChannelData()[i][j] == 0) {
          imageArray[i][j][0] = filteredImage.getRedChannelData()[i][j];
          imageArray[i][j][1] = filteredImage.getGreenChannelData()[i][j];
          imageArray[i][j][2] = filteredImage.getBlueChannelData()[i][j];
        }
        else {
          imageArray[i][j][0] = currentImage.getRedChannelData()[i][j];
          imageArray[i][j][1] = currentImage.getGreenChannelData()[i][j];
          imageArray[i][j][2] = currentImage.getBlueChannelData()[i][j];
        }
      }

    }
    Image processedImage = new Image(imageArray);
    cache.set(imageName, processedImage);

  }


}
