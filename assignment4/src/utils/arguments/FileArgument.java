package utils.arguments;

import java.io.File;

/**
 * Represents a file argument to be passed to commands in the controller.
 * Provides constructors for direct File objects or file paths.
 */
public class FileArgument implements Argument {

  private final File file;

  /**
   * Constructs a FileArgument using an existing File object.
   *
   * @param file the File object to be wrapped.
   */
  public FileArgument(File file) {
    this.file = file;
  }

  /**
   * Constructs a FileArgument from a file path string.
   *
   * @param path the path to the file as a String.
   */
  public FileArgument(String path) {
    this.file = new File(path);
  }

  public FileArgument(Object object) {
    if (object instanceof File) {
      this.file = (File) object;
    }
    else {
      throw new IllegalArgumentException("Expected a File object.");
    }
  }

  /**
   * Retrieves the file associated with this argument.
   *
   * @return the File object representing the argument.
   */
  @Override
  public File getArgumentValue() {
    return file;
  }
}
