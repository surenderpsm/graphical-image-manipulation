package model;


import java.io.IOException;

// @todo write doc
public interface ImageHandler {

//  static ImageHandler getImageHandler(String path) throws FileNotFoundException {
//    String extension;
//    int index = path.lastIndexOf('.');
//    if (index > 0) {
//      extension = path.substring(index + 1);
//    }
//    else {
//      throw new FileNotFoundException("the fiel was not found.");
//    }
//    switch (extension) {
//      case "ppm":
//        return new PPMHandler(path);
//      case "jpeg":
//      case "png":
//        return new CommonImageHandler(path);
//      default:
//        throw new IllegalArgumentException("Invalid file extension found " + extension);
//    }
//  }

  Image loadImage(String path) throws IOException;

  void saveImage(Image image, String path) throws IOException;

}
