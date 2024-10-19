package controller;

import controller.imagehandler.ImageHandler;
import java.io.IOException;
import model.arg.ArgumentType;
import model.arg.ArgumentWrapper;
import model.arg.ImageArgument;
import model.arg.IntegerArgument;
import model.arg.StringArgument;

public class ArgumentHandler {

  String[] argStrings;
  ArgumentWrapper args = new ArgumentWrapper();

  public ArgumentHandler(String s) {
    argStrings = s.split(" ");
  }

  public ArgumentWrapper getAllArguments() {
    return args;
  }

  public void prepareArguments(ArgumentType[] signature) {
    if (argStrings.length != signature.length) {
      throw new IllegalArgumentException(
          "argument count is not the same as the number of arguments");
    }
    for (int i = 0; i < argStrings.length; i++) {
      switch (signature[i]) {
        case FILE_IN:
          try {
            ImageHandler ih = ImageHandler.getImageHandler(argStrings[i]);
            ih.loadImage();
            args.addArgument(new ImageArgument(ih.getImage()));
          } catch (IOException e) {
            throw new IllegalArgumentException("File not found");
          }
          break;
        case FILE_OUT:
        case EXISTING_IMAGE:
        case NEW_IMAGE:
          /*
          A string as the name is sent as argument. This approach doesn't modify any data in the
          controller.
           */
          args.addArgument(new StringArgument(argStrings[i]));
          break;
        case INTEGER:
          try {
            args.addArgument(new IntegerArgument(Integer.parseInt(argStrings[i])));
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unexpected argument when an integer was expected.");
          }
          break;
        default:
          throw new UnsupportedOperationException(
              "Internal Error: Unsupported Operation " + "invoked in argument handling.");
      }
    }
  }
}
