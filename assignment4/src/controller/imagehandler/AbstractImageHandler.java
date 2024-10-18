package controller.imagehandler;

import java.io.File;
import java.io.FileNotFoundException;
import model.Image;

abstract class AbstractImageHandler implements ImageHandler {
  private Image image;
  private final File path;

  AbstractImageHandler(String path) throws FileNotFoundException {
    this.path = new File(path);
  }

  protected File getPath(){
    return path;
  }

  protected void setImage(Image image){
    this.image = image;
  }

  @Override
  public Image getImage(){
    return image;
  }
}
