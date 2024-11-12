package controller.arguments;

import java.io.File;

public class FileArgument implements Argument {

  private final File file;

  public FileArgument(File file) {
    this.file = file;
  }

  public FileArgument(String path) {
    this.file = new File(path);
  }

  @Override
  public File getArgumentValue() {
    return file;
  }
}
