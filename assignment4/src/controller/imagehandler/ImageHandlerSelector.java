package controller.imagehandler;

import java.lang.reflect.InvocationTargetException;

public enum ImageHandlerSelector {
  PPM("ppm", PPMHandler.class),
  PNG("png", CommonImageHandler.class),
  JPG("jpg", CommonImageHandler.class),
  JPEG("jpeg", CommonImageHandler.class),
  ;

  private final String format;
  private final Class<? extends ImageHandler> handlerClass;

  ImageHandlerSelector(String format, Class<? extends ImageHandler> handlerClass) {
    this.format = format;
    this.handlerClass = handlerClass;
  }

  public String getSupportingFormat() {
    return format;
  }

  public boolean isMatchingImageHandler(String path) {
    return parseFormat(path).equals(this.getSupportingFormat());
  }

  public ImageHandler createImageHandler(String path) {
    return this.instantiateCommand(path);
  }

  /**
   * Gets the command associated with this enum constant.
   *
   * <p>This method dynamically instantiates the command class by invoking the default
   * constructor of the command.</p>
   *
   * @return an instance of the {@link ImageHandler} associated with this enum constant
   */
  private ImageHandler instantiateCommand(String path) {
    try {
      return handlerClass.getDeclaredConstructor(String.class).newInstance(path);
    } catch (NoSuchMethodException e) {
      throw new InternalError(
          "Internal Error: The specified format " + format + " has no String constructor.");
    } catch (InvocationTargetException e) {
      // @todo Catch all exceptions thrown by constructors from Command.
      throw new UnsupportedOperationException(
          "Internal Error: There was an error in constructor invocation of handler  : "
              + e.getCause().getMessage());
    } catch (InstantiationException e) {
      throw new InternalError("Internal Error: The specified handler cannot be instantiated.");
    } catch (IllegalAccessException e) {
      throw new InternalError("Internal Error: The specified handler cannot be accessed.");
    }
  }

  private String parseFormat(String path) {
    String extension;
    int index = path.lastIndexOf('.');
    if (index > 0) {
      extension = path.substring(index + 1);
    }
    else {
      throw new UnsupportedOperationException("Format is illegal.");
    }
    return extension;
  }
}
