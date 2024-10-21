package controller.imagehandler;

import java.io.File;

abstract class AbstractImageHandler implements ImageHandler {

  private final File path;
  private final String extension;

  AbstractImageHandler(String path, String extension) {
    this.path = new File(path);
    this.extension = extension;
  }

  protected String getExtension() {return extension;}
  protected File getPath() {
    return path;
  }
}
